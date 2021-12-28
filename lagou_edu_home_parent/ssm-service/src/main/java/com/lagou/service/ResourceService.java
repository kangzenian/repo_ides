package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVO;

import java.util.List;

public interface ResourceService {

    // 资源分页&多条件查询
    public PageInfo<Resource> findAllResourceByPage(ResourceVO resourceVO);

    // 添加资源
    public void saveResource(Resource resource);

    // 修改资源
    public void updateResource(Resource resource);

    // 删除资源
    public void deleteResource(Integer id);
}
