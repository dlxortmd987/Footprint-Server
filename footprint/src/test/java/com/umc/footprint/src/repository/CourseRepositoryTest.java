package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Course;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {
    @Autowired
    CourseRepository courseRepository;

    @Test
    public void 코스_등록(){
        //given
        LocalDateTime now = LocalDateTime.now();
        courseRepository.save(Course.builder()
                .build());
        //when
        List<Course> courses = courseRepository.findAll();

        //then
        Course course = courses.get(0);

        System.out.println("createAt = " + course.getCreateAt() + ", updateAt = " + course.getUpdateAt());

        Assertions.assertThat(course.getCreateAt()).isAfter(now);
        Assertions.assertThat(course.getUpdateAt()).isAfter(now);
    }
}