package com.course.controller.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSubjectResponse implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private	String subjectName;
private	String about;
}
