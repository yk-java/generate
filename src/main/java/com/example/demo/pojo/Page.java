package com.example.demo.pojo;


/**
 * @author yk
 */
public class Page {
    int page = 1;
    int limit = 20;
    int offset = 0;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {

        return limit * (page - 1);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
