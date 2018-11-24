package com.example.demo;


import com.example.demo.security.User;
import com.example.demo.security.UserRepository;
import com.example.demo.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FlightController {

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PassengerRepository passengerRepository;



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
        model.addAttribute("passenger",new Passenger());

        return "homepage";
    }

    @RequestMapping("/search")
    public String saveFlight(@RequestParam String from, @RequestParam String to, Date departure, Date arrival, BindingResult result, Model model ){

        List<Flight> flights= flightRepository.findByFrom_CodeAndTo_Code( from,  to);
       // flightRepository.save(flight);

        if(result.hasErrors()){
            return "homepage";
        }


        model.addAttribute("flightOptions", flights);

      //  model.addAttribute("search", flightSearch);
        model.addAttribute("airports", airportRepository.findAll());

        return "list";
    }

    @RequestMapping("/allflight")
    public String seeAllFlights(Model model, Principal principal){

        User currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;
     //   Passenger currenPassenger = principal != null ? PassengerRepository.findById(id) : null;
        model.addAttribute("user", currentUser);

        model.addAttribute("flights", flightRepository.findAll());

        return "allflights";

    }

    @GetMapping("/addFlight")
    public String addFlight(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        model.addAttribute("flight", new Flight());
        model.addAttribute("airports",airportRepository.findAll());
        return "addFlight";
    }

    @PostMapping("/addFlight")
    public String processFlight(@Valid Flight flight, BindingResult result, Model model, Principal principal) {

        if (result.hasErrors()) {
            User currentUser = userRepository.findByUsername(principal.getName());
            model.addAttribute("user", currentUser);
            return "addFlight";
        }
        flightRepository.save(flight);
        return "redirect:/allflight";
    }

    @GetMapping("/reserveFlight/{flightId}")
    public String reserveFlight(@PathVariable("flightId") Long flightId, Model model, Principal principal) {
       //model.addAttribute("passenger", PassengerRepository.findById(flightId).get());
       model.addAttribute("flight", flightRepository.findById(flightId).get());
       model.addAttribute("passenger",new Passenger());

        return "bookingform";
    }

    @PostMapping("/processbook")
    public String saveBooking(@ModelAttribute("passenger")Passenger passenger){

        passengerRepository.save(passenger);
        return "test";

    }

    @RequestMapping("/delete/{id}")
    public String delFlight(@PathVariable("id") long id) {

        flightRepository.deleteById(id);
        return "allflights";
    }



//    @RequestMapping (value="/formation/qr/{id}", method = RequestMethod.GET)
//    public HttpEntity<byte[]> qr(@PathVariable Long id) {
//        byte[] bytes = QRCode.from(formationRepository.findOne(id).getTheme()
//                .toString()).withSize(120, 120).stream().toByteArray();
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        headers.setContentLength(bytes.length);
//        return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED);
//    }

}
