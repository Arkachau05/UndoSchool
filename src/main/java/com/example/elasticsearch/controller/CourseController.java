package com.example.elasticsearch.controller;

import com.example.elasticsearch.model.Course;
import com.example.elasticsearch.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.save(course));
    }

    @GetMapping("/courses/search")
    public ResponseEntity<List<Course>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortField) {
        return ResponseEntity.ok(courseService.search(keyword, page, size, sortField));
    }

    @GetMapping("/courses/suggest")
    public ResponseEntity<List<String>> suggest(@RequestParam String prefix) {
        return ResponseEntity.ok(courseService.suggest(prefix));
    }

    @GetMapping("/courses/fuzzy")
    public ResponseEntity<List<Course>> fuzzySearch(@RequestParam String title) {
        return ResponseEntity.ok(courseService.fuzzySearch(title));
    }
}
