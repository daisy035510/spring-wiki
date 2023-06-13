package com.springwiki.api.data.repository.support;

import com.springwiki.api.data.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findByName(String name);
}
