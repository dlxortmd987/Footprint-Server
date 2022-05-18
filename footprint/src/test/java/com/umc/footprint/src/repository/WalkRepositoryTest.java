package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Walk;
import com.umc.footprint.src.walks.model.ObtainedBadgeInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WalkRepositoryTest {
    @Autowired
    private WalkRepository walkRepository;

    @Test
    void walkSaveTest() {
        //given
        Walk walk = Walk.builder()
                .startAt(LocalDateTime.of(2022, 02, 21, 12, 13, 02))
                .endAt(LocalDateTime.of(2022, 02, 21, 12, 22, 42))
                .distance(10.4)
                .coordinate("78fhZRXLLO865pXs54x1VrMDihArBxsGqw+bROqqmsEDMgOftOjseEjCOqssSi++01HOx9n4rgS7XORABOTgD1tSKXAhruNeNZmfZWZsae1e/ckO4TLIaQpNwQAhs+TO")
                .pathImageUrl("2i+H0/mYIL7bT5jEPuEPFCpt4mrCBFgu1XYFzOy9/tOaMwYNWsxYcKlNCQjqeydFo+aaFbnE/q7Utdr858eXgaUAXKMgatjkO21YE5e9h4xTnV69yI6OofOBQcOiECZN")
                .userIdx(13)
                .goalRate(50.0)
                .calorie(100)
                .build();

        //when
        Walk savedWalk = walkRepository.save(walk);

        //then
        assertEquals(walk.getCoordinate(), savedWalk.getCoordinate());
    }

    @Test
    void 얻은_뱃지들_테스트() {
        //given
        int userIdx = 61;
        ObtainedBadgeInterface obtainedBadgeInterface = walkRepository.getAcquiredBadgeIdxList(userIdx);

        System.out.println("DistanceBadgeIdx = " + obtainedBadgeInterface.getDistanceBadgeIdx());
        System.out.println("RecordBadgeIdx = " + obtainedBadgeInterface.getRecordBadgeIdx());
    }
}