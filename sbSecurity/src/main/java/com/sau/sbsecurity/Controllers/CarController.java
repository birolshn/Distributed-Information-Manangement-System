package com.sau.sbsecurity.Controllers;

import com.sau.sbsecurity.DTOs.CarRentDTO;
import com.sau.sbsecurity.DTOs.CustomerRentDTO;
import com.sau.sbsecurity.Models.Car;
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
public class CarController {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final RentalRepository rentalRepository;

    public CarController(CarRepository carRepository, CustomerRepository customerRepository, RentalRepository rentalRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.rentalRepository = rentalRepository;
    }

    @GetMapping("/car")
    public String getIndex(Model model){
        Iterable<Car> carList = carRepository.findAll();
        model.addAttribute("carList", carList);
        return "car/index";
    }
    @GetMapping("/car/add")
    public String addCar(Model model){
        Car c = new Car();
        model.addAttribute("car", c);
        return "/car/addcar";
    }
    @PostMapping("car/add")
    public String carAdd(Car car){
        if(car == null) {
            System.out.println("Car is null");
            return "redirect:/car";
        }
        carRepository.save(car);
        return "redirect:/car";
    }
    @GetMapping("/car/del")
    public String deleteCar(@RequestParam("id") int id, Model model){
        Optional<Car> c = carRepository.findById(id);
        model.addAttribute("car", c);
        return "/car/delcar";
    }
    @PostMapping("/car/del")
    public String carDelete(Car car){
        if(car == null) {
            System.out.println("Car is null");
            return "redirect:/car";
        }
        carRepository.delete(car);
        return "redirect:/car";
    }

    @GetMapping("/car/update")
    public String updateCar(@RequestParam("id") int id, Model model){
        Optional<Car> c = carRepository.findById(id);
        model.addAttribute("car", c);
        return "/car/updatecar";
    }
    @PostMapping("/car/update")
    public String carUpdate(Car car){
        if(car == null) {
            System.out.println("Car is null");
            return "redirect:/car";
        }
        carRepository.save(car);
        return "redirect:/car";
    }

    @GetMapping("/car/rent")
    public String rentCarList(int id, Model model){
        CarRentDTO carRentDTO = new CarRentDTO();
        Optional<Car> car = carRepository.findById(id);
        carRentDTO.setCarId(car.get().getId());
        carRentDTO.setCarBrand(car.get().getBrand());
        carRentDTO.setCustomerList(customerRepository.findAll());
        model.addAttribute("carRentDTO", carRentDTO);
        return "car/rent";
    }
    @PostMapping("car/rent")
    public String rentCar(CarRentDTO carRentDTO){
        if(carRentDTO == null) {
            System.out.println("Customer Rent DTO is null");
            return "redirect:/car";
        }
        Rental rental = new Rental();
        rental.setCustomer(customerRepository.findById(carRentDTO.getCustomerId()).get());
        rental.setCar(carRepository.findById(carRentDTO.getCarId()).get());
        rental.setRentDate(carRentDTO.getRentDate());
        rental.setReturnDate(carRentDTO.getReturnDate());
        rental.setDeleted(false);
        rental.setReturned(false);

        rentalRepository.save(rental);
        return "redirect:/rental";
    }

}
