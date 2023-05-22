package com.sau.sbsecurity.Repositories;

import com.sau.sbsecurity.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
