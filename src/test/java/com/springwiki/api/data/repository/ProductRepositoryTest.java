package com.springwiki.api.data.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springwiki.api.data.entity.Product;

import com.springwiki.api.data.entity.QProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void basicCRUDTest() {

        /**
         * create
         */
        // given
        Product givenProduct = Product.builder()
                .name("노트")
                .price(1000)
                .stock(500)
                .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        // then
        Assertions.assertThat(savedProduct.getNumber())
                .isEqualTo(givenProduct.getNumber());
        Assertions.assertThat(savedProduct.getName())
                .isEqualTo(givenProduct.getName());
        Assertions.assertThat(savedProduct.getPrice())
                .isEqualTo(givenProduct.getPrice());
        Assertions.assertThat(savedProduct.getStock())
                .isEqualTo(givenProduct.getStock());


        /**
         * read
         */
        // when
        Product selectedProduct = productRepository.findById(savedProduct.getNumber())
                .orElseThrow(RuntimeException::new);


        // then
        Assertions.assertThat(selectedProduct.getNumber())
                .isEqualTo(givenProduct.getNumber());
        Assertions.assertThat(selectedProduct.getName())
                .isEqualTo(givenProduct.getName());
        Assertions.assertThat(selectedProduct.getPrice())
                .isEqualTo(givenProduct.getPrice());
        Assertions.assertThat(selectedProduct.getStock())
                .isEqualTo(givenProduct.getStock());


        /**
         * update
         */
        //when
        Product foundProduct = productRepository.findById(selectedProduct.getNumber())
                .orElseThrow(RuntimeException::new);

        foundProduct.setName("장난감");

        Product updatedProduct = productRepository.save(foundProduct);


        // then
        assertEquals(updatedProduct.getName(), "장난감");

        /**
         *  delete
         */
        // when
        productRepository.delete(updatedProduct);

        // then
        assertFalse(productRepository.findById(selectedProduct.getNumber()).isPresent());
    }

    @Test
    void sortingAndPagingTest() {

        // given
        Product product1 = new Product();
        product1.setName("펜");
        product1.setPrice(1000);
        product1.setStock(100);
        product1.setCreatedAt(LocalDateTime.now());
        product1.setUpdatedAt(LocalDateTime.now());

        Product product2 = new Product();
        product2.setName("펜");
        product2.setPrice(5000);
        product2.setStock(300);
        product2.setCreatedAt(LocalDateTime.now());
        product2.setUpdatedAt(LocalDateTime.now());

        Product product3 = new Product();
        product3.setName("펜");
        product3.setPrice(500);
        product3.setStock(50);
        product3.setCreatedAt(LocalDateTime.now());
        product3.setUpdatedAt(LocalDateTime.now());

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);

        System.out.println(productRepository.findByNameOrderByNumberAsc("펜"));
        System.out.println(productRepository.findByNameOrderByNumberDesc("펜"));

        // 예제 8.16
        System.out.println(productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"))));
        // 예제 8.17
        System.out.println(productRepository.findByName("펜", getSort()));

        // 페이징 쿼리 호출하는 법
        Page<Product> productPage = productRepository.findByName("연필", PageRequest.of(0,2));
        System.out.println("페이징 쿼리");
        System.out.println(productPage.getContent());

    }

    private Sort getSort() {
        return Sort.by(
                Order.asc("price"),
                Order.desc("stock")
        );
    }

    @Test
    void queryDslTest() {
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = query
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Product product : productList) {
            System.out.println("----------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("----------------");
        }
    }

    @Test
    void queryDslTest2() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Product product : productList) {
            System.out.println("----------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("----------------");
        }
    }

    @Test
    void queryDslTest3() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("연필"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (String product : productList) {
            System.out.println("----------------");
            System.out.println("Product Name : " + product);
            System.out.println("----------------");
        }

        List<Tuple> tupleList = jpaQueryFactory
                .select(qProduct.name, qProduct.price)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Tuple product : tupleList) {
            System.out.println("----------------");
            System.out.println("Product Name : " + product.get(qProduct.name));
            System.out.println("Product Name : " + product.get(qProduct.price));
            System.out.println("----------------");
        }
    }

}