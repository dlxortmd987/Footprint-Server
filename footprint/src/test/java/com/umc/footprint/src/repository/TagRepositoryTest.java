package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Hashtag;
import com.umc.footprint.src.model.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

    @Test
    void findAllByHashtagAndStatusTest() {
        //given
        String myHashtag = "wJXpTZ/waSknzS10yrveFA==";
        Hashtag savedHashtag = hashtagRepository.save(
                Hashtag.builder()
                        .hashtag(myHashtag)
                        .build()
        );
        String status = "ACTIVE";
        List<Tag> allByHashtagAndStatus = tagRepository.findAllByHashtagAndStatus(savedHashtag, status);


        //when
        List<Tag> tags = tagRepository.saveAll(allByHashtagAndStatus);
        List<String> hashtags = new ArrayList<>();
        for (Tag tag : tags) {
            Optional<Hashtag> byId = hashtagRepository.findById(tag.getHashtagIdx());
            hashtags.add(byId.get().getHashtag());
        }

        //then
        for (String h : hashtags) {
            assertEquals(myHashtag, h);
        }

    }
}