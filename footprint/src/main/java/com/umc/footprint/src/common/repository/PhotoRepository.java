package com.umc.footprint.src.common.repository;

import com.umc.footprint.src.footprints.model.entity.Footprint;
import com.umc.footprint.src.common.model.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    List<Photo> findPhotoByFootprint(Footprint footprint);

    List<Photo> findAllByUserIdx(int userIdx);

    List<Photo> findAllByFootprintAndStatus(Footprint footprint, String status);

    @Query(
            value = "select P.imageUrl " +
                    "from Photo P " +
                    "inner join Footprint F on P.footprint = F " +
                    "inner join Walk W on W = F.walk " +
                    "where F.status = 'ACTIVE' and P.status = 'ACTIVE' and W.status = 'ACTIVE' and W.walkIdx = :walkIdx"
    )
    List<String> findByWalkIdx(@Param(value = "walkIdx") Integer walkIdx);
}
