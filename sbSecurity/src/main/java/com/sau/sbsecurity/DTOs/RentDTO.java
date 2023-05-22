package com.sau.sbsecurity.DTOs;

import com.sau.sbsecurity.Models.Car;
import com.sau.sbsecurity.Models.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentDTO {
    private Iterable<Customer> customerList;
    private int customerId;
    private Iterable<Car> carList;
    private int carId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;
}
