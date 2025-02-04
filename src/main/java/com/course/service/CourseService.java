package com.course.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.course.controller.request.AddCourseRequest;
import com.course.controller.request.RemoveCourseRequest;
import com.course.controller.request.UpdateCourseRequest;
import com.course.controller.response.CourseResponse;
import com.course.controller.response.ShowCourseResponse;
import com.course.repository.CourseRepository;
import com.course.repository.entity.Course;
import com.course.repository.entity.Subject;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import com.course.controller.response.ShowSubjectResponse;

@Service
@Slf4j
public class CourseService implements ICourseService {

	@Autowired
	private CourseRepository courseRepository;
	
//	ADD COURSE
	@CacheEvict(value = "courses", allEntries = true)  // evict all entries because if we add new course the  cache that contains old data will be returned by showCourses()
	public ResponseEntity<CourseResponse> addCourse(AddCourseRequest addCourseRequest){
		Course course = courseRepository.findCourseByCourseName(addCourseRequest.getCourseName());
		if(course!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(new CourseResponse("course already exists"));
		}
		List<Subject> courseSubjects = addCourseRequest.getAddSubjectToCourse().stream()
				.map(subjectRequest -> Subject.builder()
						.subjectName(subjectRequest.getSubjectName())
						.about(subjectRequest.getAbout())
						.build())
				.collect(Collectors.toList());
		Course newCourse = Course.builder().courseName(addCourseRequest.getCourseName())
				.description(addCourseRequest.getDescription())
				.duration(addCourseRequest.getDuration())
				.fee(addCourseRequest.getFee())
				.subjects(courseSubjects).build();
			courseRepository.save(newCourse);
		return ResponseEntity.status(HttpStatus.OK).body(new CourseResponse("New course is sucessfully added"));
		
	}
// REMOVE COURSE
	@Override
	@Transactional
	@CacheEvict(value = "courses", allEntries = true)  //we need to evict all courses from cache because because while caching the courses all are stored under a same key, if want to remove particular course you must save that course under a particular key.
	public ResponseEntity<CourseResponse> removeCourse(RemoveCourseRequest remCourseReq) {
		Course courseNameToRemove = courseRepository.findCourseByCourseName(remCourseReq.getCourseName());
		if(courseNameToRemove==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CourseResponse("No such course is available"));
		}
		else {
			courseRepository.delete(courseNameToRemove); 
			return ResponseEntity.ok(new CourseResponse("Course removed successfully"));
	}
  }

//	UPDATE COURSE
	@Override
	@Transactional
	@CacheEvict(value = "courses", allEntries = true)  // evict all entries because if we update the particular course the the cache that contains old data will be returned by showCourses()
	public ResponseEntity<CourseResponse> updateCourse( String courseName , UpdateCourseRequest updateCourseRequest ) {
		Course courseToUpdate = courseRepository.findCourseByCourseName(updateCourseRequest.getCourseName());
		if(courseToUpdate != null) {
			List<Subject> courseSubjects = updateCourseRequest.getSubjects().stream()
					.map(subjectRequest -> Subject.builder()
							.subjectName(subjectRequest.getSubjectName())
							.about(subjectRequest.getAbout())
							.build())
					.collect(Collectors.toList());
			courseToUpdate = Course.builder().courseName(updateCourseRequest.getCourseName())
			.description(updateCourseRequest.getDescription())
			.duration(updateCourseRequest.getDuration())
			.fee(updateCourseRequest.getFee())
			.subjects(courseSubjects).build();
			courseRepository.save(courseToUpdate);
			return ResponseEntity.status(HttpStatus.OK).body(new CourseResponse("Course is updated sucessfully "));
			}
		return ResponseEntity.status(HttpStatus.OK).body(new CourseResponse("Something went Wrong wile updating the course "));
	}

//	SHOW COURSES
	@Override
	@Transactional
	@Cacheable(value = "courses") // Cache only the List<ShowCourseResponse> (data itself)
	public List<ShowCourseResponse> showCourses() {
	    List<Course> courses = courseRepository.findAll();
	    
	    // Prevent caching empty results
	    if (courses.isEmpty()) {
	    	log.warn("no courses are present");
	        return Collections.emptyList(); // Return an empty list for empty courses
	    }

	    // Map the courses to ShowCourseResponse objects
	    List<ShowCourseResponse> courseResponse = courses.stream().map(course -> {
	        List<ShowSubjectResponse> subjectResponse = Optional.ofNullable(course.getSubjects())
	                .orElse(Collections.emptyList())   // Empty list instead of null
	                .stream()
	                .map(subject -> ShowSubjectResponse.builder()
	                        .subjectName(subject.getSubjectName())
	                        .about(subject.getAbout())
	                        .build())
	                .collect(Collectors.toList());

	        return ShowCourseResponse.builder()
	                .courseName(course.getCourseName())
	                .description(course.getDescription())
	                .duration(course.getDuration())
	                .fee(course.getFee())
	                .showSubject(subjectResponse)
	                .build();
	    }).collect(Collectors.toList());

	    log.info("course returned sucessfully to controller");
	    // Return the list of course responses
	    return courseResponse;
	}

//	@Transactional
//	public ResponseEntity<List<ShowCourseResponse>> getCourseData() {
//	    List<ShowCourseResponse> courseResponse = showCourses(); // Get the data from the cache or DB
//
//	    if (courseResponse.isEmpty()) {
//	    	log.warn("no courses are present");
//	        return ResponseEntity.noContent().build(); // Return no content if no data
//	    }
//
//	    return ResponseEntity.ok().body(courseResponse); // Wrap the data in ResponseEntity and return
//	}

}
