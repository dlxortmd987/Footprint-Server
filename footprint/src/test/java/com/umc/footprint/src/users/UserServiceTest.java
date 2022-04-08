package com.umc.footprint.src.users;

import com.umc.footprint.config.BaseException;
import com.umc.footprint.config.EncryptProperties;
import com.umc.footprint.src.AwsS3Service;
import com.umc.footprint.src.model.User;
import com.umc.footprint.src.repository.UserRepository;
import com.umc.footprint.src.users.model.PostLoginReq;
import com.umc.footprint.src.users.model.PostLoginRes;
import com.umc.footprint.utils.AES128;
import com.umc.footprint.utils.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Context;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@EnableConfigurationProperties(EncryptProperties.class)
@TestPropertySource("/application.yml")
class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AwsS3Service awsS3Service;
    @Mock
    private EncryptProperties encryptProperties;

    @Value("${encrypt.key}")
    private String key;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userDao, userRepository, jwtService, awsS3Service, encryptProperties);
    }

    @Test
    @DisplayName("유저 확인 Service 테스트")
    public void checkUser() throws BaseException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //given

        User user = User.builder()
                .userId("42151243123")
                .email(new AES128(encryptProperties.getKey()).encrypt("test@gmail.com"))
                .providerType("google")
                .username(new AES128(encryptProperties.getKey()).encrypt("test"))
                .status("ONGOING")
                .build();

        //when
        when(userRepository.save(any(User.class))).thenReturn(user);
        User checkedUser = userService.checkUser(user);


        //then
        assertEquals("42151243123", checkedUser.getUserId());
        assertEquals("test@gmail.com", new AES128(encryptProperties.getKey()).decrypt(checkedUser.getEmail()));
        assertEquals("test", new AES128(encryptProperties.getKey()).decrypt(checkedUser.getUsername()));

    }

    @Test
    @DisplayName("로그인 Service 테스트")
    public void postUserLoginTest() throws BaseException {
        //given

        //when

        //then

    }
}