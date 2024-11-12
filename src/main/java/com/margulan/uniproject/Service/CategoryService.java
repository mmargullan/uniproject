package com.margulan.uniproject.Service;

import com.margulan.uniproject.Model.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void addCategory(String name);
}
