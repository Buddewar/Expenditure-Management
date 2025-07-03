package com.Buddewar.PracticeDemo.repository;

import com.Buddewar.PracticeDemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
