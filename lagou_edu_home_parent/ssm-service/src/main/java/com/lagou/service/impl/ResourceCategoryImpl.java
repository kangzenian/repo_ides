package com.lagou.service.impl;

import com.lagou.dao.ResourceCategoryMapper;
import com.lagou.domain.ResourceCategory;
import com.lagou.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceCategoryImpl implements ResourceCategoryService {

    @Autowired
    private ResourceCategoryMapper resourceCategoryMapper;

    /**
     * 查询所有资源分类信息
     */
    @Override
    public List<ResourceCategory> findAllResourceCategory() {
        return resourceCategoryMapper.findAllResourceCategory();
    }

    /**
     * 添加资源分类
     */
    @Override
    public void saveResourceCategory(ResourceCategory resourceCategory) {

        // 封装数据
        Date date = new Date();
        resourceCategory.setCreatedTime(date);
        resourceCategory.setUpdatedTime(date);
        if (null == resourceCategory.getCreatedBy() || "".equals(resourceCategory.getCreatedBy())) {
            resourceCategory.setCreatedBy("System");
        }
        if (null == resourceCategory.getUpdatedBy() || "".equals(resourceCategory.getUpdatedBy())) {
            resourceCategory.setUpdatedBy("System");
        }

        // 调用mapper
        resourceCategoryMapper.saveResourceCategory(resourceCategory);
    }

    /**
     * 修改资源分类
     */
    @Override
    public void updateResourceCategory(ResourceCategory resourceCategory) {

        // 封装数据
        resourceCategory.setUpdatedTime(new Date());

        // 调用mapper
        resourceCategoryMapper.updateResourceCategory(resourceCategory);
    }

    /**
     * 删除资源分类
     */
    @Override
    public void deleteResourcecategory(Integer id) {
        resourceCategoryMapper.deleteResourceCategory(id);
    }
}
