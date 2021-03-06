package com.yufan.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name="tb_content_category")
public class ContentCategory implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @Column(name = "parent_id")
    private Long parentId;


    private String name;


    @Column(name = "sort_order")
    private Integer sortOrder;


    @Column(name = "is_parent")
    private Boolean isParent;


    private Date created;


    private Date updated;


    /**
     * 定义easyui显示需要的text
     * @return
     */
    public String getText(){
        return name;
    }


    /**
     * easy显示是文件夹还是叶子节点
     * @return
     */
    public String getState(){
        return isParent==true?"closed":"open";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }
}