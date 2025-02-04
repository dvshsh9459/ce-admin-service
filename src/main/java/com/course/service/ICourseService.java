package com.course.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.course.controller.request.AddCourseRequest;
import com.course.controller.request.RemoveCourseRequest;
import com.course.controller.request.UpdateCourseRequest;
import com.course.controller.response.CourseResponse;
import com.course.controller.response.ShowCourseResponse;


public interface ICourseService {
	public ResponseEntity<CourseResponse> addCourse(AddCourseRequest addCourseRequest);
	public ResponseEntity<CourseResponse> removeCourse(RemoveCourseRequest remCourseReq);
	public ResponseEntity<CourseResponse> updateCourse(String courseName , UpdateCourseRequest updateCourseReq);
	public List<ShowCourseResponse>  showCourses();
}
