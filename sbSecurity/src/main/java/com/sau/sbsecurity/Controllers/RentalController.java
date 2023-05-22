package com.sau.sbsecurity.Controllers;

import com.sau.sbsecurity.DTOs.CarRentDTO;
import com.sau.sbsecurity.DTOs.RentDTO;
import com.sau.sbsecurity.Models.Car;
import com.sau.sbsecurity.Models.Rental;
import com.sau.sbsecurity.Repositories.CarRepository;
import com.sau.sbsecurity.Repositories.CustomerRepository;
import com.sau.sbsecurity.Repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RentalController {
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public RentalController(RentalRepository rentalRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/rental")
    public String getIndex(Model model){
        Iterable<Rental> rentalList = rentalRepository.getAll();
        model.addAttribute("rentalList", rentalList);
        return "rental/index";
    }
    @PostMapping("/rental/del")
    public String rentalDelete(int id){
        if(id <= 0) {
            System.out.println("Rental delete id problem");
            return "redirect:/rental";
        }
        rentalRepository.setDeleted(id);
        return "redirect:/rental";
    }
    @PostMapping("/rental/ret")
    public String rentalReturn(int id){
        if(id <= 0) {
            System.out.println("Rental return id problem");
            return "redirect:/rental";
        }
        rentalRepository.setReturned(id);
        return "redirect:/rental";
    }

    @GetMapping("/rental/rent")
    public String rent(Model model){
        RentDTO rentDTO = new RentDTO();
        rentDTO.setCustomerList(customerRepository.findAll());
        rentDTO.setCarList(carRepository.findAll());
        model.addAttribute("rentDTO", rentDTO);
        return "rental/rent";
    }
    @PostMapping("rental/rent")
    public String rentACar(RentDTO rentDTO){
        if(rentDTO == null) {
            System.out.println("Rent DTO is null");
            return "redirect:/rental";
        }
        Rental rental = new Rental();
        rental.setCustomer(customerRepository.findById(rentDTO.getCustomerId()).get());
        rental.setCar(carRepository.findById(rentDTO.getCarId()).get());
        rental.setRentDate(rentDTO.getRentDate());
        rental.setReturnDate(rentDTO.getReturnDate());
        rental.setDeleted(false);
        rental.setReturned(false);

        rentalRepository.save(rental);
        return "redirect:/rental";
    }

}