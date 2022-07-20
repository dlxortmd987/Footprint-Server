package com.umc.footprint.src.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Walk")
public class Walk {
    @Id
    @Column(name = "walkIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer walkIdx;

    @Column(name = "startAt", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "endAt", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "coordinate", nullable = false, columnDefinition = "LONGTEXT")
    private String coordinate;

    @Column(name = "pathImageUrl")
    private String pathImageUrl;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "userIdx", nullable = false)
    private Integer userIdx;

    @Column(name = "goalRate", nullable = false)
    private Double goalRate;

    @Column(name = "calorie")
    private Integer calorie;

    @JsonIgnore
    @OneToMany(targetEntity = Footprint.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "walk")
    private List<Footprint> footprintList = new ArrayList<>();

    @Builder
    public Walk(Integer walkIdx, LocalDateTime startAt, LocalDateTime endAt, Double distance, String coordinate, String pathImageUrl, String status, Integer userIdx, Double goalRate, Integer calorie) {
        this.walkIdx = walkIdx;
        this.startAt = startAt;
        this.endAt = endAt;
        this.distance = distance;
        this.coordinate = coordinate;
        this.pathImageUrl = pathImageUrl;
        this.status = status;
        this.userIdx = userIdx;
        this.goalRate = goalRate;
        this.calorie = calorie;
    }

    @PrePersist
    public void prePersist() {
        this.status = this.status == null ? "ACTIVE" : this.status;
    }

    public void addFootprint(Footprint footprint) {
        this.footprintList.add(footprint);
        if (footprint.getWalk() != this) {
            footprint.setWalk(this);
        }
    }

    public void changeStatus(String status) {
        this.status = status;
    }
}
