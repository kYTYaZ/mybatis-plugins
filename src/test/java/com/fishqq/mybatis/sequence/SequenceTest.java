package com.fishqq.mybatis.sequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.fishqq.mybatis.plugins.sequence.Generator;
import com.fishqq.mybatis.plugins.sequence.SequencePlugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SequenceTest {
    private Generator generator;

    @Before
    public void init() {
        this.generator = new Generator(10, SequencePlugin.DEFAULT_TABLE_NAME);
    }

    @Test
    public void generate() {
        SqlSessionFactory sqlSessionFactory = DataSourceUtil.getSessionFactory();

        DataSource dataSource = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();

        List<Long> sequences = new ArrayList<>();

        generate(dataSource, sequences);

        for (int i = 1; i < sequences.size(); i++) {
            Assert.assertEquals(sequences.get(i - 1) + 1L, (long)sequences.get(i));
        }

        print(sequences);
    }

    @Test
    public void paraelelGenerate() throws Exception {
        SqlSessionFactory sqlSessionFactory = DataSourceUtil.getSessionFactory();

        DataSource dataSource = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();

        List<Long> sequences = Collections.synchronizedList(new ArrayList<>());

        paraellelGenerate(dataSource, sequences);
    }

    private void paraellelGenerate(DataSource dataSource, List<Long> sequences) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        List<Callable<Void>> tasks = new ArrayList<>();

        List<List<Long>> sequencesByTask = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            final int c = i;

            tasks.add(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    List<Long> sequencesOfTask = new ArrayList<>();
                    sequencesByTask.add(sequencesOfTask);
                    generate(dataSource, sequencesOfTask);

                    System.out.println("sequences of task: " + (c + 1));
                    print(sequencesOfTask);

                    if (sequencesOfTask.size() > 1) {
                        for (int i = 1; i < sequencesOfTask.size(); i++) {
                            Assert.assertTrue(sequencesOfTask.get(i - 1) < sequencesOfTask.get(i));
                        }
                    }
                    return null;
                }
            });
        }

        executorService.invokeAll(tasks);

        executorService.awaitTermination(10L, TimeUnit.SECONDS);

        for (List<Long> ts : sequencesByTask) {
            sequences.addAll(ts);
        }

        Collections.sort(sequences);

        for (int i = 1; i < sequences.size(); i++) {
            Assert.assertEquals(sequences.get(i - 1) + 1L, (long)sequences.get(i));
        }
    }

    private void generate(DataSource dataSource, List<Long> sequences) {
        for (int i = 0; i < 100; i++) {
            int batch = 1 + new Random().nextInt(30);
            long start = generator.next(dataSource, batch);

            for (int j = 0; j < batch; j++) {
                long sequence = start + j;
                sequences.add(sequence);
            }
        }
    }

    private void print(List<Long> sequences) {
        sequences.forEach(s -> {
            System.out.print(s + ",");
            if (s % 10 == 0) {
                System.out.println();
            }
        });
    }
}
