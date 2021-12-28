package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    /**
     * 广告分页查询
     */
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVO promotionAdVO) {

        PageInfo<PromotionAd> pageInfo = promotionAdService.findPromotionAdByPage(promotionAdVO);
        return new ResponseResult(true, 200, "广告分页查询成功", pageInfo);
    }

    /**
     * 广告图片上传
     */
    @RequestMapping("/promotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        try {
            // 1.判断接收到的文件是否为空
            if (file.isEmpty()) {
                throw new RuntimeException();
            }

            // 2.获取项目部署路径
            String realPath = request.getServletContext().getRealPath("/");//C:\apache-tomcat-8.5.56\webapps\ssm_web\
            String substring = realPath.substring(0, realPath.indexOf("ssm_web"));//C:\apache-tomcat-8.5.56\webapps\

            // 3.获取文件原名
            // lagou.jpg
            String originalFilename = file.getOriginalFilename();

            // 4.生成新文件名
            // 12334324.jpg
            String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 5.上传文件
            String updatePath = substring + "upload\\";
            File filePath = new File(updatePath, newFileName);

            // 如果路径不存在就创建
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }

            // 上传图片
            file.transferTo(filePath);

            // 6.将文件名和文件路径返回，进行响应
            Map<String, String> map = new HashMap<>();
            map.put("fileName", newFileName);
            map.put("filePath", "http://localhost:8080/upload/" + newFileName);

            return new ResponseResult(true, 200, "图片上传成功", map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增&修改广告
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {

        if (null == promotionAd.getId()) {
            promotionAdService.savePromotionAd(promotionAd);
            return new ResponseResult(true, 200, "新建广告成功", null);
        } else {
            promotionAdService.updatePromotionAd(promotionAd);
            return new ResponseResult(true, 200, "修改广告成功", null);
        }
    }

    /**
     * 根据Id查询广告（回显广告信息）
     */
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(Integer id) {

        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);
        return new ResponseResult(true, 200, "根据ID查询广告成功", promotionAd);
    }

    /**
     * 广告动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(Integer id, Integer status) {

        promotionAdService.updatePromotionAdStatus(id, status);

        Map<String, Object> map = new HashMap<>();
        map.put("status", status);

        return new ResponseResult(true, 200, "广告动态上下线成功", map);
    }
}
