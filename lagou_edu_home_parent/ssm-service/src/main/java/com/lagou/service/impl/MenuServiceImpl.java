package com.lagou.service.impl;

import com.lagou.dao.MenuMapper;
import com.lagou.domain.Menu;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询所有父子级菜单列表
     */
    @Override
    public List<Menu> findSubMenuListByPid(Integer pid) {
        return menuMapper.findSubMenuListByPid(pid);
    }

    /**
     * 查询所有菜单列表
     */
    @Override
    public List<Menu> findAllMenu() {
        return menuMapper.findAllMenu();
    }

    /**
     * 根据ID查询菜单信息
     */
    @Override
    public Menu findMenuById(Integer id) {
        return menuMapper.findMenuById(id);
    }

    /**
     * 添加菜单
     */
    @Override
    public void saveMenu(Menu menu) {

        // 封装数据
        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);
        if (null == menu.getCreatedBy() || "".equals(menu.getCreatedBy())) {
            menu.setCreatedBy("System");
        }
        if (null == menu.getUpdatedBy() || "".equals(menu.getUpdatedBy())) {
            menu.setUpdatedBy("System");
        }

        // 调用mapper
        menuMapper.saveMenu(menu);
    }

    /**
     * 修改菜单
     */
    @Override
    public void updateMenu(Menu menu) {

        // 封装数据
        menu.setUpdatedTime(new Date());

        // 调用mapper
        menuMapper.updateMenu(menu);
    }
}
