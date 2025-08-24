package com.example.SadadApi.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SadadApi.models.SadadRecord;

@Repository
public interface SadadRecordRepository extends JpaRepository<SadadRecord, Long> {

}
