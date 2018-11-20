package com.yufan.common;

import java.util.List;

public class EasyUIResult<T> {

    //总记录数
    private Long total;

    public List<T> rows;

    public EasyUIResult() {
        super();
    }

    public EasyUIResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
