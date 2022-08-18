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
@Table(name = "Notice")
public class Notice extends BaseEntity {
    @Id
    @Column(name = "noticeIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeIdx;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "notice")
    private String notice;

    @Column(name = "image")
    private String image;

    @Column(name = "keyNotice")
    private boolean keyNotice;

    @Column(name = "status", nullable = false)
    private String status;

    @Builder
    public Notice(int noticeIdx, String title, String notice, String image, boolean keyNotice, String status) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.keyNotice = keyNotice;
        this.status = status;
    }
}
