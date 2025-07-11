package com.example.elasticsearch.repository;

import com.example.elasticsearch.model.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<Course, String> {
}
