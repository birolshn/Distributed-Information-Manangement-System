package com.sau.sbsecurity.Controllers;

import com.sau.sbsecurity.DTOs.CustomerRentDTO;
import com.sau.sbsecurity.Models.Car;
import com.sau.sbsecurity.Models.Customer;
import com.sau.sbsecurity.Models.Rental;
import com.sau.sbsecurity.Repositories.CarRepository;
import com.sau.sbsecurity.Repositories.CustomerRepository;
import com.sau.sbsecurity.Repositories.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;

    public CustomerController(CustomerRepository customerRepository, RentalRepository rentalRepository, CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
    }

    @GetMapping("/customer")
    public String getIndex(Model model){
        Iterable<Customer> customerList = customerRepository.findAll();
        model.addAttribute("customerList", customerList);
        return "customer/index";
    }

    @GetMapping("/customer/add")
    public String addCustomer(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer/addcustomer";
    }
    @PostMapping("customer/add")
    public String customerAdd(Customer customer){
        if(customer == null) {
            System.out.println("Customer is null");
            return "redirect:/customer";
        }
        customerRepository.save(customer);
        return "redirect:/customer";
    }
    @GetMapping("/customer/del")
    public String deleteCustomer(@RequestParam("id") int id, Model model){
        Optional<Customer> customer = customerRepository.findById(id);
        model.addAttribute("customer", customer);
        return "customer/delcustomer";
    }
    @PostMapping("/customer/del")
    public String customerDelete(Customer customer){
        if(customer == null) {
            System.out.println("Customer is null");
            return "redirect:/customer";
        }
        customerRepository.delete(customer);
        return "redirect:/customer";
    }

    @GetMapping("/customer/update")
    public String updateCustomer(@RequestParam("id") int id, Model model){
        Optional<Customer> customer = customerRepository.findById(id);
        model.addAttribute("customer", customer);
        return "customer/updatecustomer";
    }
    @PostMapping("/customer/update")
    public String customerUpdate(Customer customer){
        if(customer == null) {
            System.out.println("Customer is null");
            return "redirect:/customer";
        }
        customerRepository.save(customer);
        return "redirect:/customer";
    }


    @GetMapping("/customer/rent")
    public String rentCarToCustomer(int id, Model model){
        CustomerRentDTO customerRentDTO = new CustomerRentDTO();
        Optional<Customer> customer = customerRepository.findById(id);
        customerRentDTO.setCustomerId(customer.get().getId());
        customerRentDTO.setCustomerName(customer.get().getName());
        customerRentDTO.setCarList(carRepository.findAll());
        model.addAttribute("customerRentDTO", customerRentDTO);
        return "customer/rent";
    }
    @PostMapping("customer/rent")
    public String rentCar(CustomerRentDTO customerRentDTO){
        if(customerRentDTO == null) {
            System.out.println("Customer Rent DTO is null");
            return "redirect:/customer";
        }
        Rental rental = new Rental();
        rental.setCustomer(customerRepository.findById(customerRentDTO.getCustomerId()).get());
        rental.setCar(carRepository.findById(customerRentDTO.getCarId()).get());
        rental.setRentDate(customerRentDTO.getRentDate());
        rental.setReturnDate(customerRentDTO.getReturnDate());
        rental.setDeleted(false);
        rental.setReturned(false);

        rentalRepository.save(rental);
        return "redirect:/rental";
    }
}