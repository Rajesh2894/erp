package com.abm.mainet.property.service;

public interface IPropertyDeletionService {

    public int[] validatePropertyForDeletion(String proertyNo, long OrgId);

    public int deleteProperty(String proertyNo);

}
