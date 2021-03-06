package com.wish.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wish on 2018/1/19.
 */
@ApiModel("分页信息")
public class PageInfo <T>{
    @ApiModelProperty("内容")
    private List<T> items;
    @ApiModelProperty("页")
    private int page;
    @ApiModelProperty("每页条数")
    private int pageSize;
    @ApiModelProperty("总数量")
    private long totalCount;
    @ApiModelProperty("总页数")
    private long totalPage;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        if (this.pageSize > 0) {
            this.totalPage = (totalCount - 1) % this.pageSize + 1;
        }
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}

