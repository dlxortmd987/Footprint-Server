package com.umc.footprint.src.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Mark")
public class Mark {
    @Id
    @Column(name = "markIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer markIdx;

    @Column(name = "userIdx")
    private Integer userIdx;

    @Column(name = "courseIdx")
    private Integer courseIdx;

    @Column(name = "mark")
    private Boolean mark;


    @Builder
    public Mark(Integer markIdx, Integer userIdx, Integer courseIdx, Boolean mark) {
        this.markIdx = markIdx;
        this.userIdx = userIdx;
        this.courseIdx = courseIdx;
        this.mark = mark;
    }

    public void modifyMark() {
        if (this.mark) {
            this.mark = false;
        } else {
            this.mark = true;
        }
    }
}
