package com.abm.mainet.property.dao;

public interface IPropertyDeletionDao {

    int validatePropertyForBillExistOrNot(String propertyNo, long orgId);

}
