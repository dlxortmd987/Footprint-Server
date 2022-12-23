package com.umc.footprint.src.goal.model.entity;

import com.umc.footprint.utils.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Goal")
public class Goal extends BaseEntity {

    @Id
    @Column(name = "planIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planIdx;

    @Column(name = "userIdx", nullable = false)
    private Integer userIdx;

    @Column(name = "walkGoalTime", nullable = false)
    private Integer walkGoalTime;

    @Column(name = "walkTimeSlot", nullable = false)
    private Integer walkTimeSlot;

    @Builder
    public Goal(Integer planIdx, Integer userIdx, Integer walkGoalTime, Integer walkTimeSlot) {
        this.planIdx = planIdx;
        this.userIdx = userIdx;
        this.walkGoalTime = walkGoalTime;
        this.walkTimeSlot = walkTimeSlot;
    }
}
