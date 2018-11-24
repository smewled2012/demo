package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Long> {

     List<Flight> findByFrom_CodeAndTo_Code(String fromCode, String toCode);

}
