package com.haitaos.socialmedia.entity;

import lombok.Data;

@Data
public class PersonV1 {
    private String name;

    public PersonV1(String name) {
        this.name = name;
    }

}
