package com.example.huiben3.entity;

import java.util.List;

public class Book {
    private String name;
    private String path;
    private String pages;
    private Integer pageNumOfLastRead;
    private List<Page> pageList;

    public String toString() {
        return "" + this.name + "," + this.path + (pageList == null ? "" : "," +  pageList.toString());//ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Integer getPageNumOfLastRead() {
        return pageNumOfLastRead;
    }

    public void setPageNumOfLastRead(Integer pageNumOfLastRead) {
        this.pageNumOfLastRead = pageNumOfLastRead;
    }
}
