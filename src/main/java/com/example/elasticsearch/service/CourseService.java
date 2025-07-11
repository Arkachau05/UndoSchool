package com.example.elasticsearch.service;

import com.example.elasticsearch.model.Course;
import com.example.elasticsearch.repository.CourseRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private ElasticsearchOperations operations;

    public Course save(Course course) {
        return repository.save(course);
    }


    public List<Course> search(String keyword, int page, int size, String sortField) {
    Sort sort = (sortField != null && !sortField.isEmpty())
            ? Sort.by(Sort.Direction.ASC, sortField + ".keyword")
            : Sort.unsorted();

    Pageable pageable = PageRequest.of(page, size, sort);

    BoolQueryBuilder builder = QueryBuilders.boolQuery()
            .should(QueryBuilders.matchQuery("title", keyword))
            .should(QueryBuilders.matchQuery("description", keyword))
            .should(QueryBuilders.matchQuery("instructor", keyword));

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(builder)
            .withPageable(pageable)
            .build();

    return operations.search(searchQuery, Course.class)
            .stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
}





    public List<String> suggest(String prefix) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhrasePrefixQuery("title", prefix))
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<Course> searchHits = operations.search(query, Course.class);

        return searchHits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent().getTitle())
                .collect(Collectors.toList());
    }

    public List<Course> fuzzySearch(String title) {
        FuzzyQueryBuilder builder = QueryBuilders.fuzzyQuery("title", title);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .build();
        return operations.search(query, Course.class)
                .stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }
}
