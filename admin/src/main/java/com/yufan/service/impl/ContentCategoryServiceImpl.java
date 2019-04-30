package com.yufan.service.impl;

import com.yufan.bean.ContentCategory;
import com.yufan.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {
    private Logger LOGGER = LoggerFactory.getLogger(ContentCategoryServiceImpl.class);
    /**
     * 添加分类
     * @param contentCategory
     * @return 当前分类的ID
     */
    @Override
    public Long insertContentCategory(ContentCategory contentCategory) {
        contentCategory.setId(null);//防止恶意注入
        contentCategory.setIsParent(false);//设置为叶子节点
        contentCategory.setCreated(new Date());
        contentCategory.setCreated(contentCategory.getCreated());
        contentCategory.setSortOrder(1);
        //保存
        this.save(contentCategory);
        //修改父目录的状态
        ContentCategory parent = this.queryById(contentCategory.getParentId());
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            parent.setUpdated(new Date());
            this.update(parent);
        }
        return contentCategory.getId();
    }

    @Override
    public void deleteContentCategory(Long id) {
        LOGGER.debug("要删除的分类id为：{}",id);
        if(id==null){
            return;
        }
        //查找要删除的节点
        ContentCategory contentCategory = this.queryById(id);
        if (contentCategory==null){

        }
        //查找所有的子节点
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        queryAllSubNode(id,ids);

        //删除所有的子节点
        this.deleteByWhere("id",ids);
        LOGGER.debug("执行批量删除成功，删除ID为：{}",ids);

        //判断当前节点的父节点是否还有其他子节点，如果没有，isparent改为false
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = this.queryListByWhere(contentCategory);
        if (CollectionUtils.isEmpty(list)){
            ContentCategory perent = new ContentCategory();
            perent.setId(contentCategory.getParentId());
            perent.setIsParent(false);
            perent.setUpdated(new Date());
            LOGGER.debug("当前父节点没有其他子节点，修改isparent状态为false");
            this.update(perent);
        }

    }

    /**
     * 查找所有的子节点
     * @param id
     * @param ids
     */
    private void queryAllSubNode(Long id, List<Long> ids) {
        ContentCategory record = new ContentCategory();
        record.setParentId(id);
        List<ContentCategory> contentCategories = this.queryListByWhere(record);
        for (ContentCategory contentCategory : contentCategories) {
            //遍历每一个节点，将id放入集合中
            ids.add(contentCategory.getId());
            //判断该子节点是否为父节点，父节点继续递归
            if(contentCategory.getIsParent()){
                //递归
                queryAllSubNode(contentCategory.getId(),ids);
            }

        }
    }


}
