package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    /**
     * 分页查询广告信息
     */
    @Override
    public PageInfo<PromotionAd> findPromotionAdByPage(PromotionAdVO promotionAdVO) {

        PageHelper.startPage(promotionAdVO.getCurrentPage(), promotionAdVO.getPageSize());
        List<PromotionAd> promotionAdList = promotionAdMapper.findAllPromotionAdByPage();

        return new PageInfo<PromotionAd>(promotionAdList);
    }

    /**
     * 新增广告
     */
    @Override
    public void savePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);

        // 调用mapper
        promotionAdMapper.savePromotionAd(promotionAd);
    }

    /**
     * 根据Id查询广告（回显广告信息）
     */
    @Override
    public PromotionAd findPromotionAdById(Integer id) {
        return promotionAdMapper.findPromotionAdById(id);
    }

    /**
     * 修改广告信息
     */
    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {

        // 封装数据
        promotionAd.setUpdateTime(new Date());

        // 调用mapper
        promotionAdMapper.updatePromotionAd(promotionAd);
    }

    /**
     * 广告动态上下线
     */
    @Override
    public void updatePromotionAdStatus(Integer id, Integer status) {

        // 封装数据
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setId(id);
        promotionAd.setStatus(status);
        promotionAd.setUpdateTime(new Date());

        // 调用mapper
        promotionAdMapper.updatePromotionAdStatus(promotionAd);
    }
}
