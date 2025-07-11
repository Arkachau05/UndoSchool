package com.example.coursesearch.loader;

import com.example.coursesearch.document.CourseDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseIndexer implements CommandLineRunner {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void run(String... args) {
        List<CourseDocument> courses = Arrays.asList(
            CourseDocument.builder().id("1").title("Java Fundamentals").description("Learn Java").instructor("John").category("Programming").build(),
            CourseDocument.builder().id("2").title("Spring Boot").description("Microservices with Spring").instructor("Alice").category("Backend").build(),
            CourseDocument.builder().id("3").title("Elasticsearch Basics").description("Search engine course").instructor("Bob").category("Data").build()
        );

        courses.forEach(elasticsearchOperations::save);
    }
}
