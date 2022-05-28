package com.umc.footprint.src.repository;

import com.umc.footprint.src.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

}
