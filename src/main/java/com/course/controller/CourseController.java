package com.course.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.controller.request.AddCourseRequest;
import com.course.controller.request.RemoveCourseRequest;
import com.course.controller.request.UpdateCourseRequest;
import com.course.controller.response.CourseResponse;
import com.course.controller.response.ShowCourseResponse;
import com.course.service.ICourseService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	private ICourseService iCourseService;
	
@PostMapping("/add")
public ResponseEntity<CourseResponse> addCourse(@RequestBody AddCourseRequest addCourseRequest ) {  
    return iCourseService.addCourse(addCourseRequest);
}

@DeleteMapping("/remove")
public ResponseEntity<CourseResponse> removeCourse(@RequestBody RemoveCourseRequest removeCourseRequest) {
   return iCourseService.removeCourse(removeCourseRequest);
}

@PutMapping("update/{name}")
public ResponseEntity<CourseResponse> updateCourse(@PathVariable String courseName, @RequestBody UpdateCourseRequest updateCourseRequest) {
    return iCourseService.updateCourse(courseName, updateCourseRequest);
}
@GetMapping("/getAll")
public ResponseEntity<List<ShowCourseResponse>> getAllCourses() {
	 List<ShowCourseResponse> courses = iCourseService.showCourses();
    return  ResponseEntity.ok().body(courses);
}

}
