package com.Buddewar.PracticeDemo.repository;

import com.Buddewar.PracticeDemo.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Integer> {
}
