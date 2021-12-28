package com.lagou.dao;

import com.lagou.domain.PromotionAd;

import java.util.List;

public interface PromotionAdMapper {

    // 分页查询所有广告信息
    public List<PromotionAd> findAllPromotionAdByPage();

    // 新建广告
    public void savePromotionAd(PromotionAd promotionAd);

    // 根据Id查询广告（回显广告信息）
    public PromotionAd findPromotionAdById(Integer id);

    // 修改广告信息
    public void updatePromotionAd(PromotionAd promotionAd);

    // 广告动态上下线
    public void updatePromotionAdStatus(PromotionAd promotionAd);
}
