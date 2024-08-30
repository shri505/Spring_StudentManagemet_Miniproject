package com.example.Spring_MiniProject_Eg3.service;

import com.example.Spring_MiniProject_Eg3.entity.Course;
import com.example.Spring_MiniProject_Eg3.entity.Student;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface StudentServiceInterface extends UserDetailsService {

    String addUser(Student student);


    Student getUserById(int id);

    List<Student> getAllUsers();

    Student updateUser(int id, Student student);

    void deleteUser(int id);

    void enrollStudentToCourse(Course course);

}

