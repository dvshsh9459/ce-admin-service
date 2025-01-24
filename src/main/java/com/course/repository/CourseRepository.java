package com.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.repository.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
