package com.margulan.uniproject.Service.Impl;

import com.margulan.uniproject.Model.Category;
import com.margulan.uniproject.Repository.CategoryRepository;
import com.margulan.uniproject.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(String name) {
        categoryRepository.save(new Category(name));
    }
}
