package com.globits.da.repository;

import com.globits.da.domain.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Integer> {
    List<Commune> findByDistrictId(Integer id);

    Commune findByName(String communeName);
}
