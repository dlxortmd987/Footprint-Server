package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Tag;
import com.umc.footprint.src.users.model.WalkHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("select new com.umc.footprint.src.users.model.WalkHashtag(W.walkIdx, W.startAt, W.endAt, W.pathImageUrl, H.hashtag)\n" +
            "from Walk W\n" +
            "join fetch Footprint F on W.walkIdx = F.walkIdx\n" +
            "join fetch Tag T on F.footprintIdx = T.footprintIdx\n" +
            "join fetch Hashtag H on T.hashtagIdx = H.hashtagIdx\n" +
            "where F.walkIdx in\n" +
            "  (\n" +
            "    select W.walkIdx\n" +
            "    from Walk W\n" +
            "    join Footprint F on W.walkIdx = F.walkIdx\n" +
            "    join Tag T on F.footprintIdx = T.footprintIdx\n" +
            "    join Hashtag H on T.hashtagIdx = H.hashtagIdx\n" +
            "    where H.hashtag = :hashtag and W.status = 'ACTIVE' and F.status = 'ACTIVE' and T.status = 'ACTIVE'\n" +
            "  )\n" +
            "  and W.userIdx= :userIdx and W.status = 'ACTIVE' and F.status = 'ACTIVE' and T.status = 'ACTIVE'")
    List<WalkHashtag> findAllWalkAndHashtag(@Param("hashtag") String hashtag, @Param("userIdx") int userIdx);
}
