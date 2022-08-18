package com.umc.footprint.src.model;

import com.umc.footprint.utils.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "GoalDayNext")
public class GoalDayNext extends BaseEntity {

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

    @Builder
    public GoalDayNext(Integer planIdx, Integer userIdx, Integer sun, Integer mon, Integer tue, Integer wed, Integer thu, Integer fri, Integer sat) {
        this.planIdx = planIdx;
        this.userIdx = userIdx;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }

    public void setSun(Integer sun) {
        this.sun = sun;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public void setTue(Integer tue) {
        this.tue = tue;
    }

    public void setWed(Integer wed) {
        this.wed = wed;
    }

    public void setThu(Integer thu) {
        this.thu = thu;
    }

    public void setFri(Integer fri) {
        this.fri = fri;
    }

    public void setSat(Integer sat) {
        this.sat = sat;
    }

//    public void setUpdateAt(LocalDateTime updateAt) {
//        this.updateAt = updateAt;
//    }
}
