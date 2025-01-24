package com.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.course.controller.request.AddCourseRequest;
import com.course.controller.response.CourseResponse;
import com.course.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public ResponseEntity<CourseResponse> addCourse(AddCourseRequest addCourseRequest){
		return null;
		
	}

}
