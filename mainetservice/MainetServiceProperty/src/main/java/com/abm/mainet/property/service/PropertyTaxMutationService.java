/**
 * 
 */
package com.abm.mainet.property.service;

import javax.jws.WebService;

/**
 * @author Anwarul.Hassan
 * @since 04-Mar-2021
 */
@WebService
public interface PropertyTaxMutationService {
    public String MutationCheck(String uniquePropertyId);

}
