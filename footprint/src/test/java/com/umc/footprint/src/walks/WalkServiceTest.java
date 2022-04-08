package com.umc.footprint.src.walks;

import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.src.AwsS3Service;
import com.umc.footprint.src.repository.GoalNextRepository;
import com.umc.footprint.src.repository.UserRepository;
import com.umc.footprint.src.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
@DisplayName("WalkService 테스트")
public class WalkServiceTest {

    @Mock
    private WalkDao walkDao;
    @Mock
    private WalkProvider walkProvider;
    @Mock
    private UserService userService;
    @Mock
    private AwsS3Service awsS3Service;
    @Mock
    private EncryptProperties encryptProperties;
    @Mock
    private GoalNextRepository goalNextRepository;

    private WalkService walkService;

    @BeforeEach
    void setUp() {
        this.walkService = new WalkService(walkDao, walkProvider, userService, awsS3Service, encryptProperties, goalNextRepository);
    }

    @Test
    @DisplayName("saveRecord() 테스트")
    void saveRecordTest() {
        //given

    }
}
