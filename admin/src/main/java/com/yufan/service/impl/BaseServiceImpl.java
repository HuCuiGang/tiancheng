package com.yufan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yufan.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T>{
    //泛型注入
    @Autowired
    private Mapper<T> mapper;

    private Class clazz;

    public BaseServiceImpl(){
        //获取父类参数化类型 BaseService<ItemCat>
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class) parameterizedType.getActualTypeArguments()[0];
    }


    @Override
    public T queryById(Long id) {
        return  mapper.selectByPrimaryKey(id);
    }

    @Override
    public T queryByWhere(T record) {
        return mapper.selectOne(record);
    }

    @Override
    public List<T> queryListByWhere(T record) {
        return mapper.select(record);
    }

    @Override
    public PageInfo<T> queryListByWherePage(T record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<T> list = mapper.select(record);
        return new PageInfo<T>(list);
    }

    @Override
    public void save(T t) {
        mapper.insertSelective(t);
    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteById(Long id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByWhere(String property, List values) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property,values);
        mapper.deleteByExample(example);
    }
}
