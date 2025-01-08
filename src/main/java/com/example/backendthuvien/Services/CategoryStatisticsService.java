package com.example.backendthuvien.Services;

import com.example.backendthuvien.Repositories.CategoriesRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryStatisticsService {
    @Autowired
    private CategoriesRepositori categoryRepository;

    public void updateCategoryStatistics() {
        Long totalCategories = categoryRepository.countTotalCategories();


        System.out.println("Total Categories: " + totalCategories);

    }
}
