package com.fishqq.mybatis.plugins.sequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.fishqq.mybatis.plugins.InvocationInfo;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({@Signature(
    method = "update",
    type = Executor.class,
    args = {MappedStatement.class, Object.class})
})
public class SequencePlugin implements Interceptor {
    private static final Pattern INSERT_SQL_PATTERN =
        Pattern.compile("\\s*(?:insert)\\s+(?:into)\\s+(\\w+)\\W+", Pattern.CASE_INSENSITIVE);

    private Generator generator;
    public static String DEFAULT_TABLE_NAME = "sequences";
    public static long DEFAULT_STEP = 10;

    public SequencePlugin() {
        this.generator = new Generator(DEFAULT_STEP, DEFAULT_TABLE_NAME);
    }

    public SequencePlugin(long step, String tableName) {
        this.generator = new Generator(step, tableName);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        InvocationInfo invocationInfo = new InvocationInfo(invocation);

        if (isInsertSql(invocationInfo.getBoundSql().getSql())) {
            List<Sequence> sequenceParams = collectSequenceParams(invocationInfo.getParameter(), new ArrayList<>());

            sequenceParams.forEach(sequence -> {
                if (sequence.getId() == null) {
                    sequence.setId(generator.next(invocationInfo.getDataSource()));
                }
            });
        }

        Object result = invocation.proceed();

        return result;
    }

    private List<Sequence> collectSequenceParams(Object param, List<Sequence> result) {
        if (param == null) {
            return result;
        }

        if (param instanceof Sequence) {
            result.add((Sequence)param);
        } else if (param instanceof Iterable) {
            Iterator iterator = ((Iterable)param).iterator();
            while (iterator.hasNext()) {
                collectSequenceParams(iterator.next(), result);
            }
        } else if (param instanceof Map) {
            ((Map)param).forEach((key, value) -> collectSequenceParams(value, result));
        } else if (param instanceof Stream) {
            ((Stream)param).forEach(value -> collectSequenceParams(value, result));
        } else {
            //logger
        }

        // remove duplicate sequence by address
        List<Sequence> sequences = new ArrayList<>();

        result.forEach(sequence -> {
            boolean exist = false;
            for (Sequence s : sequences) {
                if (s == sequence) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                sequences.add(sequence);
            }
        });

        return sequences;
    }

    private boolean isInsertSql(String statement) {
        return INSERT_SQL_PATTERN.matcher(statement).find();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
