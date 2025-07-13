package com.example.elasticsearch.loader;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.elasticsearch.repository.CourseRepository;
import java.io.InputStream;
import java.util.List;
import com.example.elasticsearch.model.Course;

@Component
public class CourseLoader implements CommandLineRunner {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/data.json");
        if (inputStream != null) {
            List<Course> courses = objectMapper.readValue(
                inputStream, new TypeReference<List<Course>>() {});
            courseRepository.saveAll(courses);
            System.out.println("Sample courses indexed successfully!");
        } else {
            System.err.println("sample-courses.json not found in resources!");
        }
    }
}
