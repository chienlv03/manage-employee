package com.globits.da.repository;

import com.globits.da.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    boolean existsByName(String name);

    Province findByName(String provinceName);
}
