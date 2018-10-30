package com.fishqq.mybatis.sequence;

import com.fishqq.mybatis.plugins.pagination.Pagination;
import com.fishqq.mybatis.plugins.pagination.PaginationResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;

public class PaginationTest {
    @Test
    public void testPagination() {
        int pageSize = 10;
        int total = 100;

        SqlSessionFactory sqlSessionFactory = DataSourceUtil.getSessionFactory();

        SqlSession session = sqlSessionFactory.openSession(true);

        TestMapper testMapper = session.getMapper(TestMapper.class);

        testMapper.deleteAll();

        long sequence = 0;

        for (int i = 0; i < total; i++) {
            TestDo testDo = new TestDo();
            testDo.setName("name-" + (i + 1));

            int r = testMapper.insert(testDo);

            if (sequence != 0) {
                Assert.assertEquals(sequence + 1, testDo.getId().longValue());
            }

            sequence = testDo.getId();
        }

        Pagination pagination = new Pagination().setPage(1).setPageSize(pageSize);
        PaginationResult<TestDo> result = testMapper.listByPage(pagination);

        System.out.printf("total: %d, size: %d\n", result.getTotalCount(), result.size());

        Assert.assertEquals((int) result.getTotalCount(), total);
        Assert.assertEquals(result.size(), pageSize);
    }
}
