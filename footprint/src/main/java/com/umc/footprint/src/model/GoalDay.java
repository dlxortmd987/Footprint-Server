package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GoalDay")
public class GoalDay {

    @Id
    @Column(name = "planIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planIdx;

    @Column(name = "userIdx", nullable = false)
    private Integer userIdx;

    @Column(name = "sun", nullable = false)
    private Integer sun;

    @Column(name = "mon", nullable = false)
    private Integer mon;

    @Column(name = "tue", nullable = false)
    private Integer tue;

    @Column(name = "wed", nullable = false)
    private Integer wed;

    @Column(name = "thu", nullable = false)
    private Integer thu;

    @Column(name = "fri", nullable = false)
    private Integer fri;

    @Column(name = "sat", nullable = false)
    private Integer sat;

    @Column(name = "createAt")
    private LocalDateTime createAt;

    @Builder
    public GoalDay(Integer planIdx, Integer userIdx, Integer sun, Integer mon, Integer tue, Integer wed, Integer thu, Integer fri, Integer sat, LocalDateTime createAt) {
        this.planIdx = planIdx;
        this.userIdx = userIdx;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.createAt = createAt;
    }
}
