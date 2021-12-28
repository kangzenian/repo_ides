package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户分页&对条件查询
     */
    @Override
    public PageInfo<User> findAllUserByPage(UserVO userVO) {

        PageHelper.startPage(userVO.getCurrentPage(), userVO.getPageSize());
        List<User> allUser = userMapper.findAllUserByPage(userVO);
        return new PageInfo<User>(allUser);
    }

    /**
     * 修改用户状态
     */
    @Override
    public void updateUserStatus(Integer id, String status) {

        // 封装数据
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());

        // 调用mapper
        userMapper.updateUserStatus(user);
    }

    /**
     * 用户登录
     */
    @Override
    public User login(User user) throws Exception {

        // 调用mapper
        User user1 = userMapper.login(user);

        if (null != user1 && Md5.verify(user.getPassword(), "lagou", user1.getPassword())) {
            return user1;
        } else {
            return null;
        }
    }

    /**
     * 根据用户ID查询用户关联的角色信息，分配角色（回显）
     */
    @Override
    public List<Role> findUserRelationRoleById(Integer userId) {
        return userMapper.findUserRelationRoleById(userId);
    }

    /**
     * 给用户分配角色
     */
    @Override
    public void userContextRole(UserVO userVO) {

        // 1. 根据userId删除用户角色关联关系
        userMapper.deleteUserContextRole(userVO.getUserId());

        // 2.封装数据
        User_Role_relation user_role_relation = new User_Role_relation();
        user_role_relation.setUserId(userVO.getUserId());

        Date date = new Date();
        user_role_relation.setUpdatedTime(date);
        user_role_relation.setCreatedTime(date);

        if (null == userVO.getCreatedBy() || "".equals(userVO.getCreatedBy())) {
            user_role_relation.setCreatedBy("System");
        } else {
            user_role_relation.setCreatedBy(userVO.getCreatedBy());
        }
        if (null == userVO.getUpdatedBy() || "".equals(userVO.getUpdatedBy())) {
            user_role_relation.setUpdatedBy("System");
        } else {
            user_role_relation.setUpdatedBy(userVO.getUpdatedBy());
        }

        // 3. 分配角色
        for (Integer roleId : userVO.getRoleIdList()) {
            user_role_relation.setRoleId(roleId);
            userMapper.userContextRole(user_role_relation);
        }
    }

    /**
     * 根据用户ID，查询用户权限信息
     */
    @Override
    public Map<String, Object> getUserPermissions(Integer userId) {

        // 1.获取用户当前拥有的角色信息
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);

        // 判断roleList的长度是否为0（mybatis查询数据不管有没有数据，返回的集合都不为null）
        if (0 == roleList.size()) {
            // 此用户当前没有分配角色信息
            return null;
        }

        // 2.获取角色ID，保存到list集合中
        List<Integer> roleIdsList = new ArrayList<>();
        for (Role role : roleList) {
            roleIdsList.add(role.getId());
        }

        // 3.根据角色id查询父级菜单
        List<Menu> MenuList = userMapper.findParentMenuByRoleId(roleIdsList);

        // 4.查询封装父菜单关联的子菜单
        for (Menu menu : MenuList) {
            List<Menu> subMenuList = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenuList);
        }

        //  5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIdsList);

        // 6.封装数据并返回
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", MenuList);
        map.put("resourceList", resourceList);

        return map;
    }
}
