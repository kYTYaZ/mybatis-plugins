package com.fishqq.mybatis.example;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fishqq.mybatis.plugins.pagination.Pagination;
import com.fishqq.mybatis.plugins.pagination.PaginationResult;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    public static void main(String[] args) {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

            SqlSession session = sqlSessionFactory.openSession(true);
            TestMapper testMapper = session.getMapper(TestMapper.class);

            testPagination(testMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testPagination(TestMapper testMapper) {
        Pagination pagination = new Pagination().setPage(1).setPageSize(10);
        PaginationResult<TestDo> result = testMapper.listByPage(pagination);

        System.out.println(result.getTotalCount());
        for (TestDo testDo : result) {
            System.out.println(testDo);
        }
    }

    private static void testInsert(TestMapper testMapper) throws Exception {
        insertParallel(testMapper);

        Thread.sleep(10000);

        List<TestDo> testDoList = testMapper.all();
        testDoList.forEach(o -> System.out.println(o));
    }

    private static void insertParallel(TestMapper testMapper) {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Thread thread = insert(i + 1, testMapper);
            threads.add(thread);
        }

        threads.forEach(thread -> thread.start());
    }

    private static Thread insert(int t, TestMapper testMapper) {
        return new Thread(() -> {
            TestDo testDo;

            for (int i = 0; i < 100; i++) {
                testDo = new TestDo();
                testDo.setName(t + "-id-" + (i + 1));
                testMapper.insert(testDo);
                System.out.println();
            }

            System.out.println("done: " + t);
        });
    }
}
