package com.example.coursesearch.service;

import com.example.coursesearch.document.CourseDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
@RequiredArgsConstructor
public class CourseSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<CourseDocument> searchCourses(String keyword) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(keyword, "title", "description", "instructor", "category"))
                .build();
        SearchHits<CourseDocument> hits = elasticsearchOperations.search(searchQuery, CourseDocument.class);
        return hits.map(hit -> hit.getContent()).toList();
    }
}
