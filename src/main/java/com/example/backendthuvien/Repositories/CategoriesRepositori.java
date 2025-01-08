package com.example.backendthuvien.Repositories;

import com.example.backendthuvien.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepositori extends JpaRepository<Categories,Long> {
    List<Categories> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    @Query(value = "SELECT COUNT(*) FROM Categories", nativeQuery = true)
    Long countTotalCategories();


}
