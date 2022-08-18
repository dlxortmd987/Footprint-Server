package com.umc.footprint.src.model;

import com.umc.footprint.utils.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GoalNext")
public class GoalNext extends BaseEntity {

    @Id
    @Column(name = "planIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planIdx;

    @Column(name = "userIdx", nullable = false)
    private Integer userIdx;

    @Column(name = "walkGoalTime")
    private Integer walkGoalTime;

    @Column(name = "walkTimeSlot", nullable = false)
    private Integer walkTimeSlot;

    @Builder
    public GoalNext(Integer planIdx, Integer userIdx, Integer walkGoalTime, Integer walkTimeSlot) {
        this.planIdx = planIdx;
        this.userIdx = userIdx;
        this.walkGoalTime = walkGoalTime;
        this.walkTimeSlot = walkTimeSlot;
    }

    public void setWalkGoalTime(Integer walkGoalTime) {
        this.walkGoalTime = walkGoalTime;
    }

    public void setWalkTimeSlot(Integer walkTimeSlot) {
        this.walkTimeSlot = walkTimeSlot;
    }
}
