package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户分页&多条件查询
     */
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVO userVO) {

        PageInfo<User> pageInfo = userService.findAllUserByPage(userVO);
        return new ResponseResult(true, 200, "用户查询成功", pageInfo);
    }

    /**
     * 修改用户状态
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(Integer id, String status) {

        userService.updateUserStatus(id, status);
        return new ResponseResult(true, 200, "修改用户状态成功", status);
    }

    /**
     * 用户登录
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) {

        try {
            User user1 = userService.login(user);

            if (null != user1) {

                // 保存用户id及access_token到session中
                HttpSession session = request.getSession();
                String access_token = UUID.randomUUID().toString();
                session.setAttribute("access_token", access_token);
                session.setAttribute("user_id", user1.getId());

                // 将查询出来的信息响应给前台
                Map<String, Object> map = new HashMap<>();
                map.put("access_token", access_token);
                map.put("user_id", user1.getId());

                return new ResponseResult(true, 1, "登陆成功", map);
            } else {
                return new ResponseResult(true, 400, "用户名或密码错误", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据用户ID查询用户关联的角色信息，分配角色（回显）
     */
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRelationRoleById(Integer id) {

        List<Role> roleList = userService.findUserRelationRoleById(id);
        return new ResponseResult(true, 200, "查询用户关联的角色成功", roleList);
    }

    /**
     * 给用户分配角色
     */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVO userVO) {

        userService.userContextRole(userVO);
        return new ResponseResult(true, 200, "分配角色成功", null);
    }

    /**
     * 获取用户权限，进行动态菜单展示
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request) {

        // 1.获取请求头的token
        String header_token = request.getHeader("Authorization");

        // 2.获取session中的token
        Object session_token = request.getSession().getAttribute("access_token");

        // 3.判断token是否一致
        if (header_token.equals(session_token)) {
            // 获取session中的用户id
            Integer user_id = (Integer) request.getSession().getAttribute("user_id");
            // 调用service，进行菜单查询
            Map<String, Object> map = userService.getUserPermissions(user_id);

            // 判断此用户是否分配角色
            if (null == map) {
                // 没有分配角色
                return new ResponseResult(true, 200, "此用户没有分配角色信息", map);
            }
            // 分配了角色信息
            return new ResponseResult(true, 200, "获取菜单信息成功", map);
        } else {
            return new ResponseResult(false, 400, "获取菜单信息失败", null);
        }
    }
}
