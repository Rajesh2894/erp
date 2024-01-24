package com.abm.mainet.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.VENDOR_MASTER;
import com.abm.mainet.common.constant.QueryConstants;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;

@Repository
public class TbAcVendormasterDaoImpl extends AbstractDAO<TbAcVendormasterEntity> implements TbAcVendormasterDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<TbAcVendormasterEntity> getVendorData(final Long cpdVendortype, final Long cpdVendorSubType,
            final String vendor_vmvendorcode,
            final Long vmCpdStatus, final Long orgid) {

        String queryString = QueryConstants.MASTERS.TbAcVendormaster.QUERY_TO_GET_VENDOR_DATA;

        if (cpdVendortype != null) {
            queryString += " and te.cpdVendortype =:cpdVendortype";
        }

        if (cpdVendorSubType != null) {
            queryString += " and te.cpdVendorSubType =:cpdVendorSubType";
        }
        if ((vendor_vmvendorcode != null) && !vendor_vmvendorcode.isEmpty()) {
            queryString += " and te.vmVendorcode =:vendor_vmvendorcode";
        }

        if (vmCpdStatus != null) {
            queryString += " and te.vmCpdStatus =:vmCpdStatus";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter(VENDOR_MASTER.ORGID2,
                orgid);

        if (cpdVendortype != null) {
            query.setParameter(MainetConstants.VENDOR_MASTER.CPD_VENDOR_TYPE,
                    cpdVendortype);
        }
        if (cpdVendorSubType != null) {
            query.setParameter(MainetConstants.VENDOR_MASTER.VENDOR_SUB_TYPE,
                    cpdVendorSubType);
        }
        if ((vendor_vmvendorcode != null) && !vendor_vmvendorcode.isEmpty()) {
            query.setParameter(VENDOR_MASTER.VENDOR_VMVENDORCODE2,
                    vendor_vmvendorcode);
        }

        if (vmCpdStatus != null) {
            query.setParameter(VENDOR_MASTER.VM_CPD_STATUS,
                    vmCpdStatus);
        }
        List<TbAcVendormasterEntity> result = null;
        result = query.getResultList();
        return result;
    }

}
