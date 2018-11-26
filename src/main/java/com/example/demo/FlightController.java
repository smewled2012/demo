package com.example.demo;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.security.User;
import com.example.demo.security.UserRepository;
import com.example.demo.security.UserService;

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
	public String showRegistrationPage(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/register")
	public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "registration";
		}

		else {
			userService.saveUser(user);
			model.addAttribute("message", "User Account Created");
		}
		return "redirect:/";
	}

	@RequestMapping("/")
	public String homePage(Model model) {

		model.addAttribute("airports", airportRepository.findAll());
		model.addAttribute("flight", new Flight());
		model.addAttribute("passenger", new Passenger());

		model.addAttribute("flightClasses", FlightClass.values());

		return "homepage";
	}

	@RequestMapping("/search")
	public String saveFlight(@RequestParam String from, @RequestParam String to, @RequestParam String departure,
			@RequestParam(required = false) String arrival, @RequestParam TripType tripType,
			@RequestParam(required = false) Long fromSelection, @RequestParam int numberOfPassengers, Model model,
			@RequestParam FlightClass flightClass) {

		List<Flight> flights = flightRepository.findByFrom_CodeAndTo_Code(from, to);
		model.addAttribute("flightOptions", flights);

		// model.addAttribute("search", flightSearch);
		model.addAttribute("airports", airportRepository.findAll());
		model.addAttribute("tripType", tripType.toString());
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("departure", departure);
		model.addAttribute("arrival", arrival);

		model.addAttribute("fromSelection", fromSelection);
		model.addAttribute("numberOfPassengers", numberOfPassengers);
		model.addAttribute("flightClass", flightClass);

		return "list";
	}

	@RequestMapping("/allflight")
	public String seeAllFlights(Model model, Principal principal) {

		User currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;
		// Passenger currenPassenger = principal != null ?
		// PassengerRepository.findById(id) : null;
		model.addAttribute("user", currentUser);

		model.addAttribute("flights", flightRepository.findAll());

		return "allflights";

	}

	@GetMapping("/addFlight")
	public String addFlight(Model model, Principal principal) {
		User currentUser = userRepository.findByUsername(principal.getName());
		model.addAttribute("user", currentUser);
		model.addAttribute("flight", new Flight());
		model.addAttribute("airports", airportRepository.findAll());
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

	@GetMapping("/reserveFlight")
	public String reserveFlight(@RequestParam Long fromSelection, @RequestParam(required = false) Long toSelection,
			Model model, Principal principal, @RequestParam int numberOfPassengers,
			@RequestParam FlightClass flightClass)
	{
		Flight fromFlight = flightRepository.findById(fromSelection).get();
			fromFlight.setNumberOfPassengers(numberOfPassengers);
			fromFlight.setFlightClass(flightClass);
			Flight toFlight;
			model.addAttribute("fromFlight", fromFlight);
			if (null != toSelection) {
				toFlight = flightRepository.findById(toSelection).get();
				toFlight.setNumberOfPassengers(numberOfPassengers);
			toFlight.setFlightClass(flightClass);
			model.addAttribute("toFlight", toFlight);
		}
		User currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;

		model.addAttribute("passenger", new Passenger());

		return "bookingform";
	}

	@PostMapping("/processbook")
	public String saveBooking(@ModelAttribute("passenger") Passenger passenger) throws IOException, WriterException {

		passengerRepository.save(passenger);

		String firstName = passenger.getFirstName();
		String lastName  = passenger.getLastName();
		String email = passenger.getEmail();
		//String flight11 = passenger.getFlight();
		//String flightnum= flightRepository.findById(id).get().getFlightNumber();
		//String flightfrom = flight.getFrom().getName();
	//	String flightto = flight.getTo().getName();
// concatenate the strings
		String fullinformation  = firstName + lastName +email;

		generateQRCodeImage(fullinformation,350,300,"C:\\Users\\smewl\\Desktop\\QrCode\\MyQRCode.png");

		return "test";

	}

	private static void generateQRCodeImage(String text, int width, int height, String filePath)
			throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}

	@GetMapping("/reservations")
	public String seeReservation(Model model, Principal principal){

		User currentUser = principal != null ? userRepository.findByUsername(principal.getName()) : null;
		//model.addAttribute("users",userRepository.findAll());

		return "reservations";
	}


	@RequestMapping("/delete/{id}")
	public String delFlight(@PathVariable("id") long id) {

		flightRepository.deleteById(id);
		return "allflights";
	}


}
