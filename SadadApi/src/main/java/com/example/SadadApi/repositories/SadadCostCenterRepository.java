package com.example.SadadApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SadadApi.models.SadadCostCenter;

@Repository
public interface SadadCostCenterRepository extends JpaRepository<SadadCostCenter, Long>{

    List<SadadCostCenter> findAllBySadadRecordId(Long sadadRecordId);

}
