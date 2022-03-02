package com.studygroup.studygroup.web.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pagination {

    /** 페이지 당 보여지는 게시글의 최대 개수 **/
    private int pageSize = 10;

    /** 페이징된 버튼의 블럭당 최대 개수 **/
    private int blockSize = 10;

    /** 현재 페이지 **/
    private int page = 1;

    /** 현재 블럭 **/
    private int block = 1;

    /** 총 게시글 수 **/
    private int totalListCnt;

    /** 총 페이지 수 **/
    private int totalPageCnt;

    /** 총 블럭 수 **/
    private int totalBlockCnt;

    /** 블럭 시작 페이지 **/
    private int startPage = 1;

    /** 블럭 마지막 페이지 **/
    private int endPage = 1;

    /** 이전 블럭의 마지막 페이지 **/
    private int prevBlock;

    /** 다음 블럭의 시작 페이지 **/
    private int nextBlock;

    public Pagination(int totalListCnt, int page, int pageSize) {

        // 총 게시물 수	- totalListCnt
        // 현재 페이지	- page

        /** 현재 페이지 **/
        this.page = page;

        /** 한 페이지에서 보여줄 게시글 수 **/
        this.pageSize = pageSize;

        /** 총 게시글 수 **/
        this.totalListCnt = totalListCnt;

        /** 총 페이지 수 **/
        this.totalPageCnt = Math.max((int) Math.ceil((double) totalListCnt / pageSize), 1);

        /** 총 블럭 수 **/
        this.totalBlockCnt = Math.max((int) Math.ceil((double) totalPageCnt / blockSize), 1);

        /** 현재 블럭 **/
        this.block = (int) Math.ceil((double) page / blockSize);

        /** 블럭 시작 페이지 **/
        this.startPage = (block - 1) * blockSize + 1;

        /** 블럭 마지막 페이지 **/
        this.endPage = Math.min(startPage + blockSize - 1, totalPageCnt);

        /** 이전 블럭(클릭 시, 이전 블럭 마지막 페이지) **/
        this.prevBlock = Math.max((block * blockSize) - blockSize, 1);

        /** 12. 다음 블럭(클릭 시, 다음 블럭 첫번째 페이지) **/
        this.nextBlock = Math.min((block * blockSize) + 1, totalPageCnt);
    }
}
