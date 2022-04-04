package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class GroupTestSuite {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testFindAllGroups() {
        //Given
        Group group;
        //When
        for(int i = 0; i < 5; i ++) {
            String name = "Group of products: " + i;
            group = new Group(name);
            groupRepository.save(group);
        }
        //Then
        assertEquals(5, groupRepository.count());
    }

    @Test
    public void testFindGroupById() {
        //Given
        Group group = new Group("RTV");
        //When
        groupRepository.save(group);
        Long groupId = group.getId();
        Optional<Group> testId = groupRepository.findById(groupId);
        //Then
        assertEquals(groupId, testId.get().getId());
    }

    @Test
    public void testFindGroupByName() {
        // Given
        Group group = new Group("Electronics");
        // When
        groupRepository.save(group);
        Optional<Group> groupName = groupRepository.findByName("Electronics");
        // Then
        assertEquals("Electronics", groupName.get().getName());
    }

    @Test
    public void testDeleteGroupByName() {
        // Given
        Group group1 = new Group("Electronics");
        Group group2 = new Group("RTV");

        groupRepository.save(group1);
        groupRepository.save(group2);
        // When
        groupRepository.deleteByName("RTV");
        // Then
        assertEquals(1 , groupRepository.findAll().size());
    }

    @Test
    public void testDeleteGroupById() {
        // Given
        Group group1 = new Group("Electronics");
        Group group2 = new Group("RTV");
        Group group3 = new Group("Toys");

        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);
        Long group1Id = group1.getId();

        // When
        groupRepository.deleteById(group1Id);
        // Then
        assertEquals(2 , groupRepository.findAll().size());
    }

    @Test
    public void testAddProductsToGroup() {
        //Given
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Group group = new Group("Accessories");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product2);

        group.getProducts().add(product1);
        group.getProducts().add(product2);
        group.getProducts().add(product3);
        groupRepository.save(group);

        //When
        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(3, products.size());
    }

    @Test
    public void testRemoveProductsFromGroup() {
        //Given
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Group group = new Group("Accessories");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product2);

        group.getProducts().add(product1);
        group.getProducts().add(product2);
        group.getProducts().add(product3);
        groupRepository.save(group);

        //When
        group.getProducts().remove(product1);
        productRepository.delete(product1);
        group.getProducts().remove(product2);
        productRepository.delete(product2);
        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(1, products.size());
    }

    @Test
    public void testProductsAfterGroupRemoval() {
        //Given
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Group group = new Group("Accessories");

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product2);

        group.getProducts().add(product1);
        group.getProducts().add(product2);
        group.getProducts().add(product3);
        groupRepository.save(group);

        //When
        groupRepository.deleteByName("Accessories");
        List<Product> products = productRepository.findAll();

        //Then
        assertEquals(0, products.size());
    }
}
