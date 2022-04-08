package com.umc.footprint.src.walks;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.src.repository.UserRepository;
import com.umc.footprint.src.users.UserProvider;
import com.umc.footprint.src.walks.model.SaveWalk;
import com.umc.footprint.utils.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = WalkController.class)
@DisplayName("WalkController 테스트")
public class WalkControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserProvider userProvider;
    @MockBean
    private WalkProvider walkProvider;
    @MockBean
    private WalkService walkService;
    @MockBean
    private JwtService jwtService;

    @BeforeEach
    public void setUp(@Autowired WalkController walkController) {
        mockMvc = MockMvcBuilders.standaloneSetup(walkController).build();
    }

    @Test
    @DisplayName("산책 저장 테스트")
    void saveWalkTest() throws BaseException {
        //given
        List<List<Double>> coordinate = new ArrayList<List<Double>>();
        coordinate.add(Arrays.asList(10.1, 11.2));
        coordinate.add(Arrays.asList(10.5, 11.5));
        coordinate.add(Arrays.asList(11.1, 12.2));
        coordinate.add(Arrays.asList(12.1, 15.2));

        SaveWalk.builder()
                .startAt(LocalDateTime.of(2022, 3, 20, 19, 20, 10, 3333))
                .endAt(LocalDateTime.of(2022, 3, 20, 20, 20, 10, 3333))
                .distance(3.4)
                .coordinates(coordinate)
                .userIdx(109)
                .calorie(100)
                .build();

//        given(walkService.saveRecord(any()))
//                .will

        //when

        //then
    }
}
