package com.haitaos.socialmedia.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
@JsonFilter("SomeBeanFilter")
//@JsonIgnoreProperties("field1")
public class SomeBean {
    private String field1;

    //@JsonIgnore
    private String field2;

    private String field3;
}
