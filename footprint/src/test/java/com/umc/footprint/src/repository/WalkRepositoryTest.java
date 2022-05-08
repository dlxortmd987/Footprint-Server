package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Hashtag;
import com.umc.footprint.src.model.Walk;
import com.umc.footprint.src.users.model.SearchWalk;
import com.umc.footprint.src.walks.model.SearchWalkInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WalkRepositoryTest {
    @Autowired
    private WalkRepository walkRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

    @Test
    void 태그_검색_테스트() {
        //given
        String myHashtag = "wJXpTZ/waSknzS10yrveFA==";
        int userIdx = 57;
        List<Walk> allWalks = walkRepository.findAllWalks(myHashtag, userIdx);

        //when


        //then
        for (Walk s : allWalks) {
            System.out.println("WalkIdx = " + s.getWalkIdx().toString());
            System.out.println("StartAt = " + s.getStartAt().toString());
            System.out.println("EndAt = " + s.getEndAt().toString());
            System.out.println("PathImageUrl = " + s.getPathImageUrl());
        }
    }
}