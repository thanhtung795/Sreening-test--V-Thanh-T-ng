package com.example.managerteacher.mapper;

import com.example.managerteacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper  {
    List<Teacher> findAllTeacher();
    int insert(Teacher teacher);
    int update(Teacher teacher);
    int delete(String id);
}
