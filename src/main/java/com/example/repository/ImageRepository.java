package com.example.repository;

import com.example.model.Image;
import com.example.model.dto.response.ImageResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    void deleteAllByProduct_Id(Long productId);
}
