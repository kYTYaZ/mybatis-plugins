package com.fishqq.mybatis.example;

import java.util.List;

import com.fishqq.mybatis.plugins.pagination.Pagination;
import com.fishqq.mybatis.plugins.pagination.PaginationResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    Integer insert(TestDo testDo);
    List<TestDo> all();
    PaginationResult<TestDo> listByPage(Pagination pagination);
}
