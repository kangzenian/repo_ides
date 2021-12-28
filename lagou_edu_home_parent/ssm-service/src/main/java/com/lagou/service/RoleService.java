package com.lagou.service;

import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVO;

import java.util.List;

public interface RoleService {

    // 查询所有角色（根据条件）
    public List<Role> findAllRole(Role role);

    // 添加角色
    public void saveRole(Role role);

    // 修改角色
    public void updateRole(Role role);

    // 根据角色ID查询菜单信息ID
    public List<Integer> findMenuByRoleId(Integer roleId);

    // 为角色分配菜单信息
    public void RoleContextMenu(RoleMenuVO roleMenuVO);

    // 删除角色
    public void deleteRole(Integer roleId);
}
