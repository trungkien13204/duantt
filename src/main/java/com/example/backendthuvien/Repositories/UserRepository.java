package com.example.backendthuvien.Repositories;

import com.example.backendthuvien.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByPhoneNumber(String phoneNumber);


    Optional<User> findByPhoneNumber(String phoneNumber);//select*from user where phoneNumbe=?

    @Query(value = "SELECT COUNT(*) FROM Users WHERE active = 1", nativeQuery = true)
    Long countActiveUsers();
    @Query(value = "SELECT COUNT(*) FROM Users WHERE DATE(created_at) = CURRENT_DATE", nativeQuery = true)
    Long countUsersAddedToday();
    @Query(value = "SELECT COUNT(*) FROM Users", nativeQuery = true)
    Long countTotalUsers();

}
