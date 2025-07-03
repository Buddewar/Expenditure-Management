package com.Buddewar.PracticeDemo.repository;

import com.Buddewar.PracticeDemo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {


    Optional<UserInfo> findByUserId(int userId);

    Optional<UserInfo> findByUserUsername(String username);
}
