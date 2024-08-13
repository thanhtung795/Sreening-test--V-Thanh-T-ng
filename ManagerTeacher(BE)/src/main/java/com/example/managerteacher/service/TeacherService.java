package com.example.managerteacher.service;

import com.example.managerteacher.entity.Teacher;
import com.example.managerteacher.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements TeacherMapper {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findAllTeacher() {
        return teacherMapper.findAllTeacher();
    }
    @Override
    public int insert(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }

    @Override
    public int update(Teacher teacher) {
        return teacherMapper.update(teacher);
    }
    @Override
    public int delete(String id) {
        return teacherMapper.delete(id);
    }
}
