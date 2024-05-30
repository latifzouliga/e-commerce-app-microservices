package com.zouliga.controller;


import com.zouliga.customer.CustomerRequest;
import com.zouliga.customer.CustomerResponse;
import com.zouliga.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest){
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existById(@PathVariable("customer-id") String id){
        return ResponseEntity.ok(customerService.existsById(id));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String id){
        return ResponseEntity.ok(customerService.findById(id));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<?> deleteById(@PathVariable("customer-id") String id){
        customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }


}
