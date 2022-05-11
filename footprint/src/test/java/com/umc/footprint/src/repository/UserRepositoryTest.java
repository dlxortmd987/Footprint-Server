package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_유저_찿기() {
        //given
        String email = "d9B5cgwk0hvbHBZGGYVOgA==";
        User findByEmail = userRepository.findByEmail(email);

        //when
        User savedUser = userRepository.save(findByEmail);

        //then
        assertEquals(findByEmail, savedUser);
    }
}