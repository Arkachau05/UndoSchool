package com.example.coursesearch.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "courses")
public class CourseDocument {
    @Id
    private String id;
    private String title;
    private String description;
    private String instructor;
    private String category;
}
