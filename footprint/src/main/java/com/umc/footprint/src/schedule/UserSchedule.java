package com.umc.footprint.src.schedule;


import com.umc.footprint.src.users.UserDao;
import com.umc.footprint.src.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@EnableScheduling
@Configuration
public class UserSchedule {

    private final UserDao userDao;
    private final UserService userService;

    @Autowired
    public UserSchedule(UserDao userDao, UserService userService){
        this.userDao = userDao;
        this.userService = userService;
    }



    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?")
    public void changeMonthGoal(){
        userService.updateGoal();
        userService.updateGoalDay();
    }

//    @Transactional
//    @Scheduled(cron = "0 25 9 * * ?")
//    public void changeMonthGoalTest(){
//        userService.updateGoalDay();
//    }


}
