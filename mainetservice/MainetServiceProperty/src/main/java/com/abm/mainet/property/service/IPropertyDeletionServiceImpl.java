package com.abm.mainet.property.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.property.dao.IPropertyDeletionDao;
import com.abm.mainet.property.repository.PropertyDeletionRepository;

@Service
public class IPropertyDeletionServiceImpl implements IPropertyDeletionService {

    @Autowired
    PropertyDeletionRepository propertyDeletionRepository;
    @Autowired
    IPropertyDeletionDao iPropertyDeletionDao;

    @Override
    @Transactional
    public int[] validatePropertyForDeletion(String proertyNo, long OrgId) {

        int count[] = new int[2];

        count[0] = propertyDeletionRepository.vaildatePropertyExistOrNot(proertyNo, OrgId);

        count[1] = iPropertyDeletionDao.validatePropertyForBillExistOrNot(proertyNo, OrgId);

        return count;

    }

    @Override
    @Transactional
    public int deleteProperty(String propertyNo) {

        propertyDeletionRepository.deleteByAssNo(propertyNo);

        return 0;
    }

}
