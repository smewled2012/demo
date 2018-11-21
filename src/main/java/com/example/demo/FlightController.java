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

        if(userService.getUser()!=null){
            model.addAttribute("user_id",userService.getUser().getId());
        }


        return "homepage";
    }

    @PostMapping("/saveflight")
    public String saveFlight(@ModelAttribute("flight") Flight flight, BindingResult result, Model model ){

       // flightRepository.save(flight);

        if(result.hasErrors()){
            return "homepage";
        }

        List<User> users= new ArrayList<>();

        users.add(userService.getUser());

        flight.setUsers(users);

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

    @RequestMapping("/list")
    public String seeAllFlights(Model model, Principal principal){

        User currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;
        model.addAttribute("user", currentUser);

        model.addAttribute("flights", flightRepository.findAll());

        return "allflights";

    }

    @GetMapping("/addFlight")
    public String addFlight(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", currentUser);
        model.addAttribute("flight", new Flight());
        return "addFlight";
    }

    @PostMapping("/processFlight")
    public String processFlight(@Valid Flight flight, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            // -- This is to prevent "Welcome null" message in the header
            User currentUser = userRepository.findByUsername(principal.getName());
            model.addAttribute("user", currentUser);

            return "addFlight";
        }

        flightRepository.save(flight);
        return "redirect:/";
    }

    @GetMapping("/reserveFlight/{flightId}")
    public String reserveFlight(@PathVariable("flightId") Long flightId, Model model, Principal principal) {
       // Flight flight = flightRepository.findById(flightId).get();
        // User currentUser = principal != null ? userService.findByUsername(principal.getName()) : null;
       /// model.addAttribute("user", currentUser);
       model.addAttribute("user",new User());

        if(userService.getUser()!=null){
            model.addAttribute("user_id",userService.getUser().getId());
        }

        model.addAttribute("flight", flightRepository.findById(flightId).get());

        return "bookingform";
    }


    @PostMapping("/processbook")
    public String saveBooking(@ModelAttribute("user")User user, Model model){


        if(userService.getUser()!=null){
            model.addAttribute("user_id",userService.getUser().getId());
        }
        userService.saveUser(user);
        return "test";

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
