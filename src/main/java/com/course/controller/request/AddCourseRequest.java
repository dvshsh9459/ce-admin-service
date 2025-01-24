package com.course.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCourseRequest {
	private String courseName;
	private String subject;
	private int duration;
	private String description;
	private double price;

}
