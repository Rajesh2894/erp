package com.abm.mainet.rnl.service;

import com.abm.mainet.rnl.dto.EstateCitizenRatingDTO;

public interface IEstateCitizenRatingService {
    void saveRatingAndFeedback(EstateCitizenRatingDTO citizenRatingDTO);
}
