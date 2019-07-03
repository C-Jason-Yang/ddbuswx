package com.evcas.ddbuswx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by noxn on 2018/3/12.
 */
@Data
@AllArgsConstructor
public class DwzPageModel<T> {

    private long totalCount;
    private Integer numPerPage;
    private long currentPage;
    private Long totalPageNum;
    private T entity;
    private List<T> dataList;

    public DwzPageModel() {
        this.numPerPage = 10;
        this.currentPage = 1;
    }
}
