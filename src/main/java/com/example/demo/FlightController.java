package com.example.demo;


import com.example.demo.security.User;
import com.example.demo.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class FlightController {

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    UserService userService;



    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){

        if(result.hasErrors()){
            return "registration";
        }

        else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/";
    }


    @RequestMapping("/")
    public String homePage(Model model){

        model.addAttribute("airports",airportRepository.findAll());
        model.addAttribute("flight", new Flight());

        return "homepage";
    }



    @PostMapping("/saveflight")
    public String saveFlight(@ModelAttribute("flight") Flight flight, BindingResult result, Model model ){

       // flightRepository.save(flight);

        if(result.hasErrors()){
            return "homepage";
        }

    // see the list all available

      //  model.addAttribute("flights", flightRepository.findAll());

        ArrayList<Flight> flights = new ArrayList<>();
        for (Flight flight1 : flightRepository.findAll()) {
            if (flight1.getFrom().getCode().equals(flight.getFrom().getCode()) &&
                    flight1.getTo().getCode().equals(flight.getTo().getCode())) {
                flights.add(flight1);
            }
        }

        model.addAttribute("flightOptions", flights);

      //  model.addAttribute("search", flightSearch);
        model.addAttribute("airports", airportRepository.findAll());

        return "list";
    }

}
