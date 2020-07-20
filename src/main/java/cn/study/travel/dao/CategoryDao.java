package cn.study.travel.dao;

import cn.study.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    public List<Category> findAll();
}
