package com.Buddewar.PracticeDemo.repository;

import com.Buddewar.PracticeDemo.entity.AdminRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRequestRepository extends JpaRepository<AdminRequest, Integer> {
    AdminRequest findByUserId(int userId);
}
