package com.springwiki.api.data.repository;

import com.springwiki.api.data.entity.Category;
import com.springwiki.api.data.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class CategoryRepositoryTest {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void relationshipTest(){
        // 테스트 데이터 생성
        Product product = new Product();
        product.setName("펜");
        product.setPrice(2000);
        product.setStock(100);

        productRepository.save(product);

        Category category = new Category();
        category.setCode("S1");
        category.setName("도서");
        category.getProducts().add(product);

        categoryRepository.save(category);

        // 테스트 코드
        List<Product> products = categoryRepository.findById(1L).get().getProducts();

        for(Product foundProduct : products){
            System.out.println(product);
        }
    }
}