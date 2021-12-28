package com.lagou.service;

import com.lagou.dao.ResourceCategoryMapper;
import com.lagou.domain.ResourceCategory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ResourceCategoryService {

    // 查询所有资源分类信息
    public List<ResourceCategory> findAllResourceCategory();

     // 添加资源信息
    public void saveResourceCategory(ResourceCategory resourceCategory);

    // 修改资源分类
    public void updateResourceCategory(ResourceCategory resourceCategory);

    // 删除资源分类
    public void deleteResourcecategory(Integer id);
}
