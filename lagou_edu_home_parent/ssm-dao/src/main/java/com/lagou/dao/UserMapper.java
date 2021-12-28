package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface UserMapper {

    // 用户分页&多条件查询
    public List<User> findAllUserByPage(UserVO userVO);

    // 修改用户状态
    public void updateUserStatus(User user);

    // 用户登录
    public User login(User user);

    // 根据userId清除用户角色关联表信息
    public void deleteUserContextRole(Integer userId);

    // 给用户分配角色
    public void userContextRole(User_Role_relation user_role_relation);

    // --------------------- 动态获取菜单开始 ----------------------------
    // 1.根据用户ID，查询用户关联的角色信息，分配角色（回显）
    public List<Role> findUserRelationRoleById(Integer userId);

    // 2.根据角色ID，查询角色所拥有的顶级菜单
    public List<Menu> findParentMenuByRoleId(List<Integer> rids);

    // 3.根据父级菜单id，查询子级菜单
    public List<Menu> findSubMenuByPid(Integer pid);

    // 4.根据角色id，查询角色拥有的资源信息
    public List<Resource> findResourceByRoleId(List<Integer> rids);

    // --------------------- 动态获取菜单结束 ----------------------------
}
