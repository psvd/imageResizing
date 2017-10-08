package com.bijenkorf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bijenkorf.domain.Image;

public interface ImageRepository extends JpaRepository<Image, String> {
			

}
