package com.course.controller.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCourseRequest {
	private String courseName;
	private String duration;
	private String description;
	private double fee;
	private List<AddSubjectRequest> subjects;
}
