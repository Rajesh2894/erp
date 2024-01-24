package com.abm.mainet.common.dao;

import java.util.List;

/**
 * @author Vivek.Kumar
 * @since 06-Feb-2016
 */
public interface CommonDao {

    List<Object> getSequenceProc(Object[] ipValues, int[] sqlTypes);

    List<Object> getJavaSequenceProc(Object[] ipValues, int[] sqlTypes);
    
    //added by @Sadik.shaikh
    //to generate the custom sequence number 
    List<Object> getCustSequenceProc(final Object[] ipValues, final int[] sqlTypes);
}
