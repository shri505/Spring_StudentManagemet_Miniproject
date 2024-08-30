package com.example.Spring_MiniProject_Eg3.service;

import com.example.Spring_MiniProject_Eg3.entity.Course;
import com.example.Spring_MiniProject_Eg3.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Spring_MiniProject_Eg3.entity.Student;
import com.example.Spring_MiniProject_Eg3.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements StudentServiceInterface {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student> userDetail = studentRepository.findByUsername(username);
        return userDetail.map(StudentDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public String addUser(Student student) {
        Optional<Student> optionalValue = studentRepository.findByUsername(student.getUsername());
        if (optionalValue.isPresent()) {
            return "User already Registered";
        }
        student.setPassword(encoder.encode(student.getPassword()));
        studentRepository.save(student);
        return "User Added Successfully";
    }


    @Override
    public Student getUserById(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Student> getAllUsers() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateUser(int id, Student student) {
        Student existingUser = studentRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(student.getUsername());
            existingUser.setEmail(student.getEmail());
            if (!existingUser.getPassword().equals(student.getPassword())) {
                existingUser.setPassword(encoder.encode(student.getPassword()));
            }
            return studentRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(int id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void enrollStudentToCourse(Course course) {
        Student studentFromCourse = course.getStudent();

        if (studentFromCourse == null) {
            throw new RuntimeException("Student is not provided in the course.");
        }

        Student existingStudent = studentRepository.findById(studentFromCourse.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        course.setStudent(existingStudent);

        courseRepository.save(course);
    }
}
