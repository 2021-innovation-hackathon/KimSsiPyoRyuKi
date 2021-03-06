package com.hocket.modules.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByHocketIdOrderByAddDateTimeDesc(Long id);

    Image findByUrl(String url);
}
