package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVO;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色（根据条件）
     */
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {

        List<Role> allRole = roleService.findAllRole(role);
        return new ResponseResult(true, 200, "查询所有角色成功", allRole);
    }

    /**
     * 添加&修改角色
     */
    @RequestMapping("/saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role) {

        if (null == role.getId()) {
            roleService.saveRole(role);
            return new ResponseResult(true, 200, "添加角色成功", null);
        } else {
            roleService.updateRole(role);
            return new ResponseResult(true, 200, "修改角色成功", null);
        }
    }

    @Autowired
    private MenuService menuService;

    /**
     * 查询所有父子级菜单列表
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {

        // -1:表示顶级节点的父节点Id
        List<Menu> menuList = menuService.findSubMenuListByPid(-1);

        HashMap<String, Object> map = new HashMap<>();
        map.put("parentMenuList", menuList);

        return new ResponseResult(true, 200, "查询所有父子级菜单列表成功", map);
    }

    /**
     * 根据角色ID查询菜单信息ID
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId) {

        List<Integer> menuIdList = roleService.findMenuByRoleId(roleId);
        return new ResponseResult(true, 200, "查询菜单信息Id成功", menuIdList);
    }


    /**
     * 为角色设置菜单信息
     */
    @RequestMapping("/roleContextMenu")
    public ResponseResult RoleContextMenu(@RequestBody RoleMenuVO roleMenuVO) {

        roleService.RoleContextMenu(roleMenuVO);
        return new ResponseResult(true, 200, "为角色设置菜单成功", null);
    }

    /**
     * 删除角色
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id) {

        roleService.deleteRole(id);
        return new ResponseResult(true, 200, "删除角色成功", null);
    }
}
