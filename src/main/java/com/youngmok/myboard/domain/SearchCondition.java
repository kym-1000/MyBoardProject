package com.youngmok.myboard.domain;

import lombok.*;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String keyword="";
    private String option="";
    private String sortoption="";

    public SearchCondition(){}
    public SearchCondition(Integer page, Integer pageSize, String keyword, String option,String sortoption) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
        this.sortoption = sortoption;
    }

    public Integer getOffset() {
        return (page-1)*pageSize;
    }

    public String getQueryString(Integer page) {
        return     UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("pageSize",pageSize)
                .queryParam("option",option)
                .queryParam("sortoption",sortoption)
                .queryParam("keyword",keyword)
                .build().toString();
    }

    public String getQueryString() {
        return   getQueryString(page);
    }
}

