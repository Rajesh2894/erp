package com.abm.mainet.swm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.swm.repository.VehicleLogBookRepository;

@Service
public class VehicleLogBookService implements IVehicleLogBookService{
    
    @Autowired
    private VehicleLogBookRepository vehicleLogBookRepository;

  
}
