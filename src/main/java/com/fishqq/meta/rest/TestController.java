package com.fishqq.meta.rest;

import com.alibaba.dt.onedata.datasource.DataSourceTypeEnum;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/10/18
 */
@RestController
@RequestMapping("/datasources")
@CrossOrigin(value = "*")
public class TestController {
    public static class DataSourceDto {
        public String name;
        public String desc;
        public DataSourceTypeEnum type;
        public String jdbcUrl;
        public String database;
        public String schema;
        public String userName;
        public String password;

        @Override
        public String toString() {
            return "DataSourceDto{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", database='" + database + '\'' +
                ", schema='" + schema + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
        }
    }

    @PostMapping
    public String create(@RequestBody DataSourceDto dataSourceDto) {
        System.out.println(dataSourceDto);
        return "";
    }
}
