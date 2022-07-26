package com.umc.footprint.src.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GoalNext")
public class GoalNext {

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

    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "updateAt", nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public GoalNext(Integer planIdx, Integer userIdx, Integer walkGoalTime, Integer walkTimeSlot, LocalDateTime createAt, LocalDateTime updateAt) {
        this.planIdx = planIdx;
        this.userIdx = userIdx;
        this.walkGoalTime = walkGoalTime;
        this.walkTimeSlot = walkTimeSlot;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public void setWalkGoalTime(Integer walkGoalTime) {
        this.walkGoalTime = walkGoalTime;
    }

    public void setWalkTimeSlot(Integer walkTimeSlot) {
        this.walkTimeSlot = walkTimeSlot;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
