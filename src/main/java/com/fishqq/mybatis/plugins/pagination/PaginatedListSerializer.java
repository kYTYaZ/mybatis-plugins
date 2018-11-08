package com.fishqq.mybatis.plugins.pagination;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fishqq.mybatis.plugins.pagination.PaginatedList;

/**
 * @author 白路 bailu.zjj@alibaba-inc.com
 * @date 2018/11/8
 */
public class PaginatedListSerializer extends StdSerializer<PaginatedList> {
    public PaginatedListSerializer() {
        this(null);
    }

    public PaginatedListSerializer(Class<PaginatedList> t) {
        super(t);
    }

    @Override
    public void serialize(PaginatedList paginatedList, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("total", paginatedList.getTotal());
        if (paginatedList.size() > 0) {
            jsonGenerator.writeArrayFieldStart("data");
            for (Object item : paginatedList) {
                jsonGenerator.writeObject(item);
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndObject();
    }
}
