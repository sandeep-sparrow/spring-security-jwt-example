package com.example.service;

import com.example.entity.Product;
import com.example.entity.UserInfo;
import com.example.repository.IUserInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {

    List<Product> productList = null;
    private final IUserInfoRepository iUserInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadProductFromDb(){
        productList= IntStream.rangeClosed(1,100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product "+ i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000))
                        .build()).collect(Collectors.toList());
    }

    public List<Product> getProducts() {
        return productList;
    }

    public Product getProduct(int id) {
        return productList.stream()
                .filter(p -> p.getProductId() == id)
                .findAny()
                .orElseThrow(()-> new RuntimeException("product "+id+" not found"));
    }

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        iUserInfoRepository.save(userInfo);
        return "UserInfo Added to System!";
    }
}
