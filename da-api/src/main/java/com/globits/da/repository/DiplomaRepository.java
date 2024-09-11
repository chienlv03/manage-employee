package com.globits.da.repository;

import com.globits.da.domain.Diploma;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiplomaRepository extends JpaRepository<Diploma, Integer> {
    boolean existsByEmployeeAndTypeAndProvinceAndEndDateAfter(Employee employee, String type, Province province, LocalDate endDate);

    int countByEmployeeAndTypeAndEndDateAfter(
            Employee employee, String type, LocalDate date);

    List<Diploma> findByEmployeeId(Integer employeeId);

    List<Diploma> findByProvinceId(Integer provinceId);
}
