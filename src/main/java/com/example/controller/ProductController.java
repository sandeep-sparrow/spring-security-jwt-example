package com.example.controller;

import com.example.entity.AuthRequest;
import com.example.entity.Product;
import com.example.entity.UserInfo;
import com.example.service.JwtService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable("id") int id){
        return productService.getProduct(id);
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return productService.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }else{
            throw new UsernameNotFoundException("Invalid User request!");
        }
    }

}
