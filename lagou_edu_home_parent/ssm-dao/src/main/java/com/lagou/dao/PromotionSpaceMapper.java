package com.lagou.dao;

import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionSpaceMapper {

    // 查询所有广告位
    public List<PromotionSpace> findAllPromotionSpace();

    // 添加广告位
    public void savePromotionSpace(PromotionSpace promotionSpace);

    // 根据ID查询广告位信息（回显广告位名称）
    public PromotionSpace findPromotionSpaceById(int id);

    // 修改广告位名称
    public void updatePromotionSpace(PromotionSpace promotionSpace);
}
