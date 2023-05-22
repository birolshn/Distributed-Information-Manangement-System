package com.sau.sbsecurity.Repositories;

import com.sau.sbsecurity.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
