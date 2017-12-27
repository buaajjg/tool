package com.znph.saas.sys.dto;

/**
 * Created by zhiboliu2 on 2017/9/21.
 */
public class CriterionPageDto {
	
	static final int pageSize = 50;
	
    private Integer page;
    private Integer size;
    private Integer index;

    public CriterionPageDto() {
        this.page = 1;
        this.size = pageSize;
        this.index = 0;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getIndex() {
        if(this.page == null)
            this.page = 1;
        if(this.size == null)
            this.size = pageSize;
        if(this.index == null)
            this.index = 0;
        this.index = (page - 1) * size;
        return this.index;
    }

}
