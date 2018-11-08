package com.fishqq.mybatis.sequence;

import java.util.List;

import com.fishqq.mybatis.plugins.pagination.Pagination;
import com.fishqq.mybatis.plugins.pagination.PaginatedList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    Integer insert(TestDo testDo);
    List<TestDo> all();
    PaginatedList<TestDo> listByPage(Pagination pagination);
    void deleteAll();
}
