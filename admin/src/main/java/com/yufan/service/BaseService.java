package com.yufan.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {
    //根据id查询一个
    public T queryById(Long id);

    //根据指定的条件查询一个
    public T queryByWhere(T record);

    //根据指定的条件查询多个
    public List<T> queryListByWhere(T record);

    //根据指定的条件查询多个(带分页)
    public PageInfo<T> queryListByWherePage(T record,int pageNum,int pageSize);

    //保存
    public void save(T t);

    //修改
    public void update(T t);

    //删除
    public void deleteById(Long id);

    //根据指定的条件批量删除
    public void deleteByWhere(String property,List<Object> values);
}
