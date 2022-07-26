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

    @Column(name = "isKey")
    private boolean key;

    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "updateAt", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "status", nullable = false)
    private String status;

    @Builder
    public Notice(int noticeIdx, String title, String notice, String image, boolean key, LocalDateTime createAt, LocalDateTime updateAt, String status) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.notice = notice;
        this.image = image;
        this.key = key;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }
}
