package com.example.backendthuvien.Services;

import com.example.backendthuvien.DTO.categoryDTO;
import com.example.backendthuvien.Repositories.CategoriesRepositori;
import com.example.backendthuvien.entity.Categories;
import com.example.backendthuvien.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategorisService implements iCategorisService{
    @Autowired
    private CategoriesRepositori categoriesRepositori;
    @Override
    public Categories createCategory(categoryDTO categories) {
        if (categoriesRepositori.existsByName(categories.getName())) {
            throw new CustomException("400", "Tên danh mục đã tồn tại");
        }
        Categories newCategory=Categories.builder()
                .name(categories.getName())
                .build();
        return categoriesRepositori.save(newCategory);
    }

    @Override
    public Categories getCategoryById(long id) {
        return categoriesRepositori.findById(id).orElseThrow(()->new CustomException("404", "Danh mục không tồn tại"));
    }

    @Override
    public List<Categories> getAllCategories() {
        List<Categories> categories = categoriesRepositori.findAll();
        if (categories.isEmpty()) {
            throw new CustomException("404", "Danh mục rỗng");
        }
        return categories;
    }

    @Override
    public Categories updateCategories(@Validated long CategoriesId, @RequestBody categoryDTO categories) {
        Categories existingCate = categoriesRepositori.findById(CategoriesId)
                .orElseThrow(() -> new CustomException("404", "Danh mục không tồn tại"));

        // Kiểm tra tên có rỗng không
        if (categories.getName() == null || categories.getName().trim().isEmpty()) {
            throw new CustomException("400", "Tên danh mục không được để trống");
        }
        existingCate.setName(categories.getName());
        categoriesRepositori.save(existingCate);
        return existingCate;

    }

    @Override
    public void deleteCategories(long id) {
        // Kiểm tra xem danh mục có tồn tại không
        Categories existingCategory = categoriesRepositori.findById(id)
                .orElseThrow(() -> new CustomException("404", "Danh mục không tồn tại"));

        // Xóa danh mục
        categoriesRepositori.deleteById(existingCategory.getId());
    }

    public List<Categories> searchCategoriesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new CustomException("400", "Tên tìm kiếm không được để trống");
        }
        List<Categories> result = categoriesRepositori.findByNameContainingIgnoreCase(name);
        if (result.isEmpty()) {
            throw new CustomException("404", "Không tìm thấy danh mục phù hợp");
        }
        return result;
    }

}
