package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
        //given
        User user = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test@gmail.com")
                .providerType("google")
                .build();

        //when
        User savedUser = userRepository.save(user);

        //then
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    public void existsByEmailSuccessTest() {
        //given
        User user = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test@gmail.com")
                .providerType("google")
                .build();
        User loginUser = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test@gmail.com")
                .providerType("google")
                .build();

        //when
        User savedUser = userRepository.save(user);
        boolean existFlag = userRepository.existsByEmail(loginUser.getEmail());

        //then
        assertEquals(true, existFlag);

    }

    @Test
    public void existsByEmailFailureTest() {
        //given
        User user = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test@gmail.com")
                .providerType("google")
                .build();
        User loginUser = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test1123@gmail.com")
                .providerType("google")
                .build();

        //when
        User savedUser = userRepository.save(user);
        boolean existFlag = userRepository.existsByEmail(loginUser.getEmail());

        //then
        assertEquals(false, existFlag);

    }

    @Test
    public void findByEmailTest() {
        //given
        User user = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test@gmail.com")
                .providerType("google")
                .build();

        //when
        User savedUser = userRepository.save(user);
        Optional<User> userFindByEmail = userRepository.findByEmail(user.getEmail());

        //then
        userFindByEmail.ifPresent(value -> assertEquals(savedUser.getEmail(), value.getEmail()));
    }

    @Test
    public void findByUserIdTest() {
        //given
        User user = User.builder()
                .userId("42151243123")
                .username("test")
                .email("test@gmail.com")
                .providerType("google")
                .build();

        //when
        User savedUser = userRepository.save(user);
        Optional<User> userFindByEmail = userRepository.findByUserId(user.getUserId());

        //then
        userFindByEmail.ifPresent(value -> assertEquals(savedUser.getUserId(), value.getUserId()));
    }
}