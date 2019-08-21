package com.wzkj.hzyp.common;

import java.io.Serializable;
import java.util.List;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class DataGrid implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long total;
    public List<?> rows;

    public DataGrid() {
    }

    public DataGrid(List<?> rows) {
        this.rows = rows;
    }

    public DataGrid(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
