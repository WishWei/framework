package com.wish.model.vo;

import java.io.Serializable;

/**
 * Created by wish on 2018/1/17.
 */
public class TestVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
