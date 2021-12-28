package com.lagou.service.impl;

import com.lagou.dao.PromotionSpaceMapper;
import com.lagou.domain.PromotionSpace;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PromotionSpaceServiceImpl implements PromotionSpaceService {

    @Autowired
    private PromotionSpaceMapper promotionSpaceMapper;

    /**
     * 查询所有广告位
     */
    @Override
    public List<PromotionSpace> findAllPromotionSpace() {
        return promotionSpaceMapper.findAllPromotionSpace();
    }

    /**
     * 添加广告位
     */
    @Override
    public void savePromotionSpace(PromotionSpace promotionSpace) {

        // 补全数据
        promotionSpace.setSpaceKey(UUID.randomUUID().toString());
        Date date = new Date();
        promotionSpace.setCreateTime(date);
        promotionSpace.setUpdateTime(date);
        promotionSpace.setIsDel(0);

        // 调用dao实现添加
        promotionSpaceMapper.savePromotionSpace(promotionSpace);
    }

    /**
     * 根据ID查询广告位（回显广告位名称）
     */
    @Override
    public PromotionSpace findPromotionSpaceById(int id) {
        return promotionSpaceMapper.findPromotionSpaceById(id);
    }

    /**
     * 修改广告位名称
     */
    @Override
    public void updatePromotionSpace(PromotionSpace promotionSpace) {

        // 补全数据
        promotionSpace.setUpdateTime(new Date());

        // 实现修改
        promotionSpaceMapper.updatePromotionSpace(promotionSpace);
    }
}
