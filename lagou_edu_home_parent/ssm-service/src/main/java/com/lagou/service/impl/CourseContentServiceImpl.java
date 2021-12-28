package com.lagou.service.impl;

import com.lagou.dao.CourseContentMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    /**
     * 根据课程id查询课程内容（章节+课时）
     */
    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId) {
        return courseContentMapper.findSectionAndLessonByCourseId(courseId);
    }

    /**
     * 回显章节对应的课程信息
     */
    @Override
    public Course findCourseByCourseId(Integer courseId) {
        return courseContentMapper.findCourseByCourseId(courseId);
    }

    /**
     * 新增章节信息
     */
    @Override
    public void saveSection(CourseSection section) {

        // 补全信息
        Date date = new Date();
        section.setCreateTime(date);
        section.setUpdateTime(date);

        // 调用dao层实现新增
        courseContentMapper.saveSection(section);
    }

    /**
     * 修改章节信息
     */
    @Override
    public void updateSection(CourseSection section) {

        // 补全信息
        section.setUpdateTime(new Date());

        // 实现修改
        courseContentMapper.updateSection(section);
    }

    /**
     * 修改章节状态
     */
    @Override
    public void updateSectionStatus(int id, int status) {

        // 封装数据
        CourseSection courseSection = new CourseSection();
        courseSection.setId(id);
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());

        // 实现修改
        courseContentMapper.updateSectionStatus(courseSection);
    }

    /**
     * 新增课时信息
     */
    @Override
    public void saveLesson(CourseLesson lesson) {

        // 补全数据
        Date date = new Date();
        lesson.setCreateTime(date);
        lesson.setUpdateTime(date);

        // 实现新增
        courseContentMapper.saveLesson(lesson);
    }

    /**
     * 修改课时信息
     */
    @Override
    public void updateLesson(CourseLesson lesson) {

        // 补全数据
        lesson.setUpdateTime(new Date());

        // 实现修改
        courseContentMapper.updateLesson(lesson);
    }
}
