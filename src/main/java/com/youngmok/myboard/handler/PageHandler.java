package com.youngmok.myboard.handler;

import com.youngmok.myboard.domain.SearchCondition;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PageHandler {
    private SearchCondition sc;
    private int naviSize = 10; // 페이지 내비게이션의 크기
    private int totalPage; // 전체 페이지의 갯수
    private int totalCnt;   // 총 게시물 갯수
    private int beginPage; // 내비게이션의 첫번째페이지
    private int endPage;  // 내비게이션의 마지막페이지
    private boolean showPrev; // 이전 페이지 여부
    private boolean showNext; // 다음 페이지 여부


    public PageHandler(int totalCnt, SearchCondition sc) {
        this.totalCnt = totalCnt;
        this.sc = sc;

        doPaging(totalCnt, sc);
    }


    public void doPaging(int totalCnt, SearchCondition sc) {
        this.totalCnt = totalCnt;

        totalPage = (int) Math.ceil(totalCnt / (double) sc.getPageSize());

        beginPage = (sc.getPage() - 1) / naviSize * naviSize + 1;

        endPage = Math.min(beginPage + naviSize - 1, totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }

//    void print() {
//        System.out.println("page = " + sc.getPage());
//        System.out.println(showPrev ? "[PREV]" : "");
//        for (int i = beginPage; i <= endPage; i++) {
//            System.out.print(i + " ");
//        }
//        System.out.print(showNext ? "[NEXT]" : "");
//    }

}