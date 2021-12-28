package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询所有菜单列表
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {

        List<Menu> allMenu = menuService.findAllMenu();
        return new ResponseResult(true, 200, "查询所有菜单成功", allMenu);
    }

    /**
     * 根据ID查询菜单信息(回显)
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {

        // 查询父子菜单信息
        List<Menu> subMenuList = menuService.findSubMenuListByPid(-1);

        HashMap<String, Object> map = new HashMap<>();

        // 判断参数id的值是否为-1，-1表示添加
        if (-1 == id) {
            // 添加 只回显父级菜单
            map.put("menuInfo", null);
            map.put("parentMenuList", subMenuList);

            // 响应数据
            return new ResponseResult(true, 200, "添加回显成功", map);
        } else {
            // 修改
            Menu menuInfo = menuService.findMenuById(id);

            map.put("menuInfo", menuInfo);
            map.put("parentMenuList", subMenuList);

            // 响应数据
            return new ResponseResult(true, 200, "修改回显成功", map);
        }
    }

    /**
     * 添加&修改菜单
     */
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu) {

        if (null == menu.getId()) {
            menuService.saveMenu(menu);
            return new ResponseResult(true, 200, "添加菜单成功", null);
        } else {
            menuService.updateMenu(menu);
            return new ResponseResult(true, 200, "修改菜单成功", null);
        }
    }
}
