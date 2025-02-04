package com.course.repository.entity;

import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "Course_Name",unique = true)
	private String courseName;
	private String duration;
	private String description;
	private double fee;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;
}
