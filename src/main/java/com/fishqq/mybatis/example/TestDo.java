package com.fishqq.mybatis.example;

import com.fishqq.mybatis.plugins.sequence.Sequence;

public class TestDo implements Sequence {
    private Long id;
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestDo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
