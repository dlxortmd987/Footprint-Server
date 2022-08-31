package com.umc.footprint.src.common.repository;

import com.umc.footprint.src.model.Footprint;
import com.umc.footprint.src.common.model.entity.Tag;
import com.umc.footprint.src.users.model.WalkHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("select new com.umc.footprint.src.users.model.WalkHashtag(W.walkIdx, W.startAt, W.endAt, W.pathImageUrl, H.hashtag)\n" +
            "from Walk W\n" +
            "join fetch Footprint F on W.walkIdx = F.walk.walkIdx\n" +
            "join fetch Tag T on F.footprintIdx = T.footprint.footprintIdx\n" +
            "join fetch Hashtag H on T.hashtag.hashtagIdx = H.hashtagIdx\n" +
            "where F.walk.walkIdx in\n" +
            "  (\n" +
            "    select W.walkIdx\n" +
            "    from Walk W\n" +
            "join Footprint F on W.walkIdx = F.walk.walkIdx\n" +
            "join Tag T on F.footprintIdx = T.footprint.footprintIdx\n" +
            "join Hashtag H on T.hashtag.hashtagIdx = H.hashtagIdx\n" +
            "    where (H.hashtag like %:hashtag%) and W.status = 'ACTIVE' and F.status = 'ACTIVE' and T.status = 'ACTIVE'\n" +
            "  )\n" +
            "  and W.userIdx= :userIdx and W.status = 'ACTIVE' and F.status = 'ACTIVE' and T.status = 'ACTIVE'")
    List<WalkHashtag> findAllWalkAndHashtag(@Param("hashtag") String hashtag, @Param("userIdx") int userIdx);

    List<Tag> findByFootprint(Footprint footprint);

    List<Tag> findAllByUserIdx(int userIdx);

    List<Tag> findAllByFootprintAndStatus(Footprint footprint, String status);

}
