package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVO;
import com.lagou.domain.Role_menu_relation;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色（根据条件）
     */
    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }

    /**
     * 添加角色
     */
    @Override
    public void saveRole(Role role) {

        // 封装数据
        Date date = new Date();
        role.setUpdatedTime(date);
        role.setCreatedTime(date);
        if (null == role.getCreatedBy() || "".equals(role.getCreatedBy())) {
            role.setCreatedBy("System");
        }
        if (null == role.getUpdatedBy() || "".equals(role.getUpdatedBy())) {
            role.setUpdatedBy("System");
        }

        // 调用mapper
        roleMapper.saveRole(role);
    }

    /**
     * 修改角色
     */
    @Override
    public void updateRole(Role role) {

        // 补全信息
        role.setUpdatedTime(new Date());

        // 调用mapper
        roleMapper.updateRole(role);
    }

    /**
     * 根据角色ID查询菜单信息ID
     */
    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }

    /**
     * 为角色设置菜单信息
     */
    @Override
    public void RoleContextMenu(RoleMenuVO roleMenuVO) {

        // 1.清除roleId对应的角色菜单表中的关联信息
        Integer roleId = roleMenuVO.getRoleId();
        roleMapper.deleteRoleContextMenu(roleId);

        // 2.为角色设置菜单
        Role_menu_relation role_menu_relation = new Role_menu_relation();
        // 遍历menuId
        for (Integer menuId : roleMenuVO.getMenuIdList()) {

            // 封装数据
            role_menu_relation.setRoleId(roleId);
            role_menu_relation.setMenuId(menuId);

            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);

            if (null == roleMenuVO.getCreatedBy() || "".equals(roleMenuVO.getCreatedBy())) {
                role_menu_relation.setCreatedBy("System");
            } else {
                role_menu_relation.setCreatedBy("roleMenuVO.getCreatedBy()");
            }
            if (null == roleMenuVO.getUpdatedBy() || "".equals(roleMenuVO.getUpdatedBy())) {
                role_menu_relation.setUpdatedBy("System");
            } else {
                role_menu_relation.setUpdatedBy("roleMenuVO.getUpdatedBy()");
            }

            // 3.调用mapper
            roleMapper.RoleContextMenu(role_menu_relation);
        }
    }

    /**
     * 删除角色
     */
    @Override
    public void deleteRole(Integer roleId) {

        // 删除中间表关联关系
        roleMapper.deleteRoleContextMenu(roleId);

        // 删除角色
        roleMapper.deleteRole(roleId);
    }
}
