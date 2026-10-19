package com.mrudul.tourandtravel.repository;

import com.mrudul.tourandtravel.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Optional<Customer> findByCustEmail(String custEmail);

    boolean existsByCustEmail(String custEmail);

    boolean existsByCustPhone(String custPhone);
}
