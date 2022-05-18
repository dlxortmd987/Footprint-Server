package com.umc.footprint.src.repository;

import com.umc.footprint.src.users.model.WalkHashtag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void 태그_검색_쿼리_테스트() {
        //given
        String myHashtag = "wJXpTZ/waSknzS10yrveFA==";
        Integer userIdx = 57;
        List<WalkHashtag> allWalks = tagRepository.findAllWalkAndHashtag(myHashtag, userIdx);

        //when


        //then
        for (WalkHashtag w : allWalks) {
            String displayName = w.getEndAt().getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
            String date = w.getEndAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")).replace(".0", ". ");
            String time = w.getEndAt().format(DateTimeFormatter.ofPattern("HH:mm")).replace(".0", ". ");
            System.out.println("WalkIdx = " + w.getWalkIdx().toString());
            System.out.println("StartAt = " + w.getStartAt().toString());
            System.out.println("EndAt = " + w.getEndAt().toString());
            System.out.println("date = " + date + " " + displayName);
            System.out.println("time = " + time);
            System.out.println("displayName = " + displayName);
            System.out.println("PathImageUrl = " + w.getPathImageUrl());
            System.out.println("Hashtag = " + w.getHashtag());
        }
    }
}