package com.example.managerteacher.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private String teacherID;
    private String lastName;
    private String firstName;
    private boolean  teacherType;
    private String qualification;
    private double  baseSalary;
    private Date employmentDate;
    private boolean employmentStatus;
    private String image;

}
