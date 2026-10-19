package com.mrudul.tourandtravel.controller;


import com.mrudul.tourandtravel.dto.request.CustomerLoginRequest;
import com.mrudul.tourandtravel.dto.request.CustomerRegisterRequest;
import com.mrudul.tourandtravel.dto.response.CustomerResponse;
import com.mrudul.tourandtravel.dto.request.CustomerUpdateRequest;
import com.mrudul.tourandtravel.entity.Customer;
import com.mrudul.tourandtravel.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody CustomerRegisterRequest request) {

        // Check email
        if (customerRepository.existsByCustEmail(
                request.getCustEmail())) {

            return ResponseEntity
                    .badRequest()
                    .body("Email already registered");
        }


        // Check phone
        if (customerRepository.existsByCustPhone(
                request.getCustPhone())) {

            return ResponseEntity
                    .badRequest()
                    .body("Phone number already registered");
        }


        Customer customer = new Customer();

        customer.setCustName(request.getCustName());
        customer.setCustEmail(request.getCustEmail());
        customer.setCustPhone(request.getCustPhone());
        customer.setCustAddress(request.getCustAddress());
        customer.setCustGender(request.getCustGender());
        customer.setImageUrl(request.getImageUrl());


        // Hash password before storing
        customer.setPassword(
                passwordEncoder.encode(request.getPassword())
        );


        Customer savedCustomer = customerRepository.save(customer);

        // Create response without password
        CustomerResponse response =
                new CustomerResponse(
                        savedCustomer.getCustId(),
                        savedCustomer.getCustName(),
                        savedCustomer.getCustEmail(),
                        savedCustomer.getCustPhone(),
                        savedCustomer.getCustAddress(),
                        savedCustomer.getCustGender(),
                        savedCustomer.getImageUrl()
                );


        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody CustomerLoginRequest request) {

        Customer customer =
                customerRepository
                        .findByCustEmail(request.getCustEmail())
                        .orElse(null);


        if (customer == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }


        // Check password
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        customer.getPassword()
                );


        if (!passwordMatches) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }


        return ResponseEntity.ok(customer);
    }


    // get current customer
    @GetMapping("/{custId}")
    public ResponseEntity<?> getCustomer(
            @PathVariable Integer custId) {

        Customer customer =
                customerRepository
                        .findById(custId)
                        .orElse(null);

        if (customer == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        CustomerResponse response =
                new CustomerResponse(
                        customer.getCustId(),
                        customer.getCustName(),
                        customer.getCustEmail(),
                        customer.getCustPhone(),
                        customer.getCustAddress(),
                        customer.getCustGender(),
                        customer.getImageUrl()
                );

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{custId}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable Integer custId,
            @RequestBody CustomerUpdateRequest request) {

        Customer customer =
                customerRepository
                        .findById(custId)
                        .orElse(null);

        if (customer == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (!customer.getCustEmail().equals(request.getCustEmail())
                && customerRepository.existsByCustEmail(
                request.getCustEmail())) {

            return ResponseEntity
                    .badRequest()
                    .body("Email already registered");
        }

        if (!customer.getCustPhone().equals(request.getCustPhone())
                && customerRepository.existsByCustPhone(
                request.getCustPhone())) {

            return ResponseEntity
                    .badRequest()
                    .body("Phone number already registered");
        }

        customer.setCustEmail(request.getCustEmail());
        customer.setCustPhone(request.getCustPhone());
        customer.setCustAddress(request.getCustAddress());
        customer.setImageUrl(request.getImageUrl());


        Customer updatedCustomer = customerRepository.save(customer);

        // Return updated customer without password
        CustomerResponse response =
                new CustomerResponse(
                        updatedCustomer.getCustId(),
                        updatedCustomer.getCustName(),
                        updatedCustomer.getCustEmail(),
                        updatedCustomer.getCustPhone(),
                        updatedCustomer.getCustAddress(),
                        updatedCustomer.getCustGender(),
                        updatedCustomer.getImageUrl()
                );

        return ResponseEntity.ok(response);
    }
}
