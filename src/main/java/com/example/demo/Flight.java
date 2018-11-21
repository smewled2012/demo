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
    private Double price;
    private String aircraft;
    private String tripClass;
    private int numPassenger;
    private String tripType;

    @ManyToMany
    private List<User> users;

    public Flight() {

        users= new ArrayList<>();
    }

    public Flight(String flightNumber, Airport from, Airport to, String departure, String arrival,
                  Integer duration, Double price, String aircraft, String tripClass, int numPassenger, String tripType) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.price = price;
        this.aircraft = aircraft;
        this.tripClass = tripClass;
        this.numPassenger = numPassenger;
        this.tripType = tripType;
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

    public String getTripClass() {
        return tripClass;
    }

    public void setTripClass(String tripClass) {
        this.tripClass = tripClass;
    }

    public int getNumPassenger() {
        return numPassenger;
    }

    public void setNumPassenger(int numPassenger) {
        this.numPassenger = numPassenger;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
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
}
