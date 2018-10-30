package com.fishqq.meta.rest;

import java.util.function.Supplier;

import com.alibaba.dt.dataphin.common.lang.result.ResultDTO;
import com.alibaba.dt.dataphin.meta.client.util.ResultUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/9/13
 */
public class ApiLogger {
    private static Logger logger = LoggerFactory.getLogger(ApiLogger.class);

    public <T> ResultDTO<T> log(Supplier<T> supplier, String api, Object ...args) {
        long start = System.currentTimeMillis();

        try {
            T result = supplier.get();

            long end = System.currentTimeMillis();

            logger.info("call {}.{}({}) spend {} ms", this.getClass().getSimpleName(), api, args, end - start);

            return ResultUtil.ok(result);
        } catch (Throwable e) {
            logger.error("call api {}.{}({}) error: {}", this.getClass().getSimpleName(), api, args, e.getMessage());
            logger.error("detail: ", e);

            return ResultUtil.exception(e);
        }
    }
}
