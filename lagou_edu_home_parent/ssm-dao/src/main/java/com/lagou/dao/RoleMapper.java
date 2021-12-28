package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

public interface RoleMapper {

    // 查询所有角色（根据条件）
    public List<Role> findAllRole(Role role);

    // 添加角色
    public void saveRole(Role role);

    // 修改角色
    public void updateRole(Role role);

    // 根据角色ID查询菜单信息ID
    public List<Integer> findMenuByRoleId(Integer roleId);

    // 根据roleId删除角色菜单中间表关联信息
    public void deleteRoleContextMenu(Integer roleId);

    // 为角色分配菜单信息
    public void RoleContextMenu(Role_menu_relation role_menu_relation);

    // 删除角色
    public void deleteRole(Integer roleId);
}
