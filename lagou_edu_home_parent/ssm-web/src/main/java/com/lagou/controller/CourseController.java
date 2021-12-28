package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 多条件课程列表查询
     */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO) {

        // 调用service
        List<Course> list = courseService.findCourseByCondition(courseVO);
        return new ResponseResult(true, 200, "响应成功", list);
    }


    /**
     * 课程图片上传
     */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        try {
            // 1.判断接收到的文件是否为空
            if (file.isEmpty()) {
                throw new RuntimeException();
            }

            // 2.获取项目部署路径
            String realPath = request.getServletContext().getRealPath("/");//C:\apache-tomcat-8.5.56\webapps\ssm_web\
            String substring = realPath.substring(0, realPath.indexOf("ssm_web"));//C:\apache-tomcat-8.5.56\webapps\

            // 3.获取文件原名
            // lagou.jpg
            String originalFilename = file.getOriginalFilename();

            // 4.生成新文件名
            // 12334324.jpg
            String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 5.上传文件
            String updatePath = substring + "upload\\";
            File filePath = new File(updatePath, newFileName);

            // 如果路径不存在就创建
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }

            // 上传图片
            file.transferTo(filePath);

            // 6.将文件名和文件路径返回，进行响应
            Map<String, String> map = new HashMap<>();
            map.put("fileName", newFileName);
            map.put("filePath", "http://localhost:8080/upload/" + newFileName);

            return new ResponseResult(true, 200, "图片上传成功", map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增&修改课程信息及讲师信息
     */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) {

        try {
            if (null == courseVO.getId()) {
                // 新增操作
                courseService.saveCourseOrTeacher(courseVO);
                return new ResponseResult(true, 200, "添加成功", null);
            } else {
                // 修改操作
                courseService.updateCourseOrTeacher(courseVO);
                return new ResponseResult(true, 200, "修改成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据id查询课程信息及关联的讲师信息，进行数据回显
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id) {

        CourseVO courseVO = courseService.findCourseById(id);
        return new ResponseResult(true, 200, "根据id查询课程信息成功",courseVO);
    }

    /**
     * 修改课程状态
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(int id, int status) {

        // 调用service传递参数，完成课程状态变更
        courseService.updateCourseStatus(id, status);

        // 响应数据
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        return new ResponseResult(true, 200, "课程状态变更成功", map);
    }
}
