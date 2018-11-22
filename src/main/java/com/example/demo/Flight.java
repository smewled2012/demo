package com.example.demo;


import com.example.demo.security.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String flightNumber;
    @ManyToOne
    private Airport from;
    @ManyToOne
    private Airport to;

    private String departure;
    //    private Date departure;
    private String arrival;
    //    private Date arrival;
    private Integer duration;
    private Double priceEconomy;
    private Double priceBusiness;
    private Double priceFirstclass;
    private String aircraft;


    @ManyToMany
    private List<User> users;

    @OneToOne
    private Passenger passenger;

    public Flight() {

        users= new ArrayList<>();
    }

    public Flight(String flightNumber, Airport from, Airport to, String departure, String arrival,
                  Integer duration, Double priceEconomy, Double priceBusiness, Double priceFirstclass, String aircraft) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.priceEconomy = priceEconomy;
        this.priceBusiness = priceBusiness;
        this.priceFirstclass = priceFirstclass;
        this.aircraft = aircraft;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPriceEconomy() {
        return priceEconomy;
    }

    public void setPriceEconomy(Double priceEconomy) {
        this.priceEconomy = priceEconomy;
    }

    public Double getPriceBusiness() {
        return priceBusiness;
    }

    public void setPriceBusiness(Double priceBusiness) {
        this.priceBusiness = priceBusiness;
    }

    public Double getPriceFirstclass() {
        return priceFirstclass;
    }

    public void setPriceFirstclass(Double priceFirstclass) {
        this.priceFirstclass = priceFirstclass;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Passenger getPassenger() {
        return passenger;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
