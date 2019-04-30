package com.yufan.service;

import com.yufan.bean.ContentCategory;

import java.util.List;

public interface ContentCategoryService extends BaseService<ContentCategory> {
    public Long insertContentCategory(ContentCategory contentCategory);
    public void deleteContentCategory(Long id);
}
