package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    /**
     * 根据课程id查询课程内容（章节+课时）
     */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(@RequestParam Integer courseId) {

        // 调用service
        List<CourseSection> list = courseContentService.findSectionAndLessonByCourseId(courseId);
        return new ResponseResult(true, 200, "章节及课时信息查询成功", list);
    }

    /**
     * 回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(@RequestParam Integer courseId) {

        // 调用service
        Course course = courseContentService.findCourseByCourseId(courseId);
        return new ResponseResult(true, 200, "查询课程信息成功", course);
    }

    /**
     * 新增&修改章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection section) {

        // 判断是否携带了章节id
        if (null == section.getId()) {
            // 新增章节
            courseContentService.saveSection(section);
            return new ResponseResult(true, 200, "新增章节成功", null);
        } else {
            // 修改章节
            courseContentService.updateSection(section);
            return new ResponseResult(true, 200, "修改章节成功", null);
        }
    }

    /**
     * 修改章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(int id, int status) {

        courseContentService.updateSectionStatus(id, status);

        // 响应数据
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        return new ResponseResult(true, 200, "修改章节状态成功", map);
    }

    /**
     * 新增&修改课时信息
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson lesson) {

        if (null == lesson.getId()) {
            // 新增课时
            courseContentService.saveLesson(lesson);
            return new ResponseResult(true, 200, "新增课时成功", null);
        } else {
            // 修改课时
            courseContentService.updateLesson(lesson);
            return new ResponseResult(true, 200, "修改课时成功", null);
        }
    }
}
