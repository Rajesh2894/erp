package com.abm.mainet.care.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.care.domain.CareFeedback;
import com.abm.mainet.care.repository.CareFeedbackRepository;

@Service
public class CareFeedbackService implements ICareFeedbackService {

    @Autowired
    private CareFeedbackRepository careFeedbackRepository;

    @Override
    public CareFeedback getFeedbackByApplicationId(String applicationID) {
        return careFeedbackRepository.getFeedbackByApplicationId(applicationID);
    }

    @Override
    public void saveCareFeedbak(CareFeedback cf) {

        CareFeedback dbcf = careFeedbackRepository.getFeedbackByApplicationId(cf.getTokenNumber());
        if (dbcf != null)
            cf.setId(dbcf.getId());
        cf.setFeedbackDate(new Date());
        careFeedbackRepository.save(cf);
    }
}
