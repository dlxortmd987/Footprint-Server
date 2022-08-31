package com.umc.footprint.src.footprints.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.umc.footprint.src.common.model.entity.Tag;
import com.umc.footprint.src.walks.model.entity.Walk;
import com.umc.footprint.utils.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Footprint")
public class Footprint extends BaseEntity {

    @Id
    @Column(name = "footprintIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer footprintIdx;

    @Column(name = "coordinate", length = 100, nullable = false)
    private String coordinate;

    @Column(name = "record", columnDefinition = "LONGTEXT")
    private String record;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "onWalk", nullable = false)
    private Integer onWalk;

    @JsonIgnore
    @OneToMany(targetEntity = Tag.class, fetch = FetchType.LAZY, mappedBy = "footprint")
    private List<Tag> tagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walkIdx")
    private Walk walk;

    @Builder
    public Footprint(Integer footprintIdx, String coordinate, String record, String status, Integer onWalk) {
        this.footprintIdx = footprintIdx;
        this.coordinate = coordinate;
        this.record = record;
        this.status = status;
        this.onWalk = onWalk;
    }

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? "ACTIVE" : this.status;
    }

    public void addTagList(Tag tag) {
        this.getTagList().add(tag);
        // 무한 루프에 빠지지 않게 체크
        if (tag.getFootprint() != this) {
            tag.setFootprint(this);
        }
    }

    public void setWalk(Walk walk) {
        this.walk = walk;
        if (!walk.getFootprintList().contains(this)) {
            walk.getFootprintList().add(this);
        }
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void changeStatus(String status) {
        this.status = status;
    }

    public void recordDecrypt(String decrypt) {
        this.record = decrypt;
    }

}
