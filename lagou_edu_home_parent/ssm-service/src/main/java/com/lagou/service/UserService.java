package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    // 用户分页&多条件查询
    public PageInfo<User> findAllUserByPage(UserVO userVO);

    // 修改用户状态
    public void updateUserStatus(Integer id, String status);

    // 用户登录
    public User login(User user) throws Exception;

    // 根据用户ID查询用户关联的角色信息，分配角色（回显）
    public List<Role> findUserRelationRoleById(Integer userId);

    // 给用户分配角色
    public void userContextRole(UserVO userVO);

    // 根据用户ID，动态获取菜单信息
    public Map<String, Object> getUserPermissions(Integer userId);
}
