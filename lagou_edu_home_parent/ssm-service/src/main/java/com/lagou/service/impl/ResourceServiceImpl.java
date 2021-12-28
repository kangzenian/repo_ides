package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.ResourceMapper;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVO;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 资源分页&多条件查询
     */
    @Override
    public PageInfo<Resource> findAllResourceByPage(ResourceVO resourceVO) {

        PageHelper.startPage(resourceVO.getCurrentPage(), resourceVO.getPageSize());
        List<Resource> allResourceList = resourceMapper.findAllResourceByPage(resourceVO);
        return new PageInfo<>(allResourceList);

    }

    /**
     * 添加资源
     */
    @Override
    public void saveResource(Resource resource) {

        // 封装数据
        Date date = new Date();
        resource.setUpdatedTime(date);
        resource.setCreatedTime(date);
        if (null == resource.getCreatedBy() || "".equals(resource.getCreatedBy())) {
            resource.setCreatedBy("System");
        }
        if (null == resource.getUpdatedBy() || "".equals(resource.getUpdatedBy())) {
            resource.setUpdatedBy("System");
        }

        // 调用mapper
        resourceMapper.saveResource(resource);
    }

    /**
     * 修改资源
     */
    @Override
    public void updateResource(Resource resource) {

        // 封装数据
        resource.setUpdatedTime(new Date());

        // 调用mapper
        resourceMapper.updateResource(resource);
    }

    /**
     * 删除资源
     */
    @Override
    public void deleteResource(Integer id) {
        resourceMapper.deleteResource(id);
    }
}
