package com.abm.mainet.care.service;

import com.abm.mainet.care.domain.CareFeedback;

public interface ICareFeedbackService {

	CareFeedback getFeedbackByApplicationId(String tokenNumber);

	void saveCareFeedbak(CareFeedback cf);

}
