package com.umc.footprint.src.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Photo")
// ToString 사용 시 무한 재귀 주의, 양방향 연관관계 추가 필요
//@ToString(exclude = {"footprint"})
public class Photo {

    @Id
    @Column(name = "photoIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoIdx;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "userIdx")
    private Integer userIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "footprintIdx")
    private Footprint footprint;

    @Builder
    public Photo(Integer photoIdx, String imageUrl, String status, Integer userIdx) {
        this.photoIdx = photoIdx;
        this.imageUrl = imageUrl;
        this.status = status;
        this.userIdx = userIdx;
    }

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? "ACTIVE" : this.status;
    }

    public void setFootprint(Footprint footprint) {
        this.footprint = footprint;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void changeStatus(String status) {
        this.status = status;
    }


}
