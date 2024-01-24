package com.abm.mainet.rnl.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.rnl.domain.EstateCitizenRating;
import com.abm.mainet.rnl.dto.EstateCitizenRatingDTO;
import com.abm.mainet.rnl.repository.EstateCitizenRatingRepository;

@Service
public class EstateCitizenRatingServiceImpl implements IEstateCitizenRatingService {

    @Autowired
    EstateCitizenRatingRepository citizenRatingRepository;

    @Transactional
    public void saveRatingAndFeedback(EstateCitizenRatingDTO citizenRatingDTO) {
        EstateCitizenRating rating = new EstateCitizenRating();
        BeanUtils.copyProperties(citizenRatingDTO, rating);
        citizenRatingRepository.save(rating);
    }

}
