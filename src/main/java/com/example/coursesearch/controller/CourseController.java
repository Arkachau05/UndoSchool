package com.example.coursesearch.controller;

import com.example.coursesearch.document.CourseDocument;
import com.example.coursesearch.service.CourseSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseSearchService searchService;

    @GetMapping("/search")
    public List<CourseDocument> search(@RequestParam String keyword) {
        return searchService.searchCourses(keyword);
    }
}
