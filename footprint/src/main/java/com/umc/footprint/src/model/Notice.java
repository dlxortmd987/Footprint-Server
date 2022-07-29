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
@Table(name = "Notice")
public class Notice {
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

    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "updateAt", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Builder
    public Notice(int noticeIdx, String title, String notice, String image, boolean keyNotice, LocalDateTime createAt, LocalDateTime updateAt, String status) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.keyNotice = keyNotice;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
