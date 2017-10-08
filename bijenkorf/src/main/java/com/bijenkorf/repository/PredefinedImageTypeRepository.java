package com.bijenkorf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bijenkorf.domain.PredefinedImageType;

public interface PredefinedImageTypeRepository extends JpaRepository<PredefinedImageType, Long>{
	
	public String findByPredefinedImageTypeName(String predefinedImageTypeName);

}
