package com.abm.mainet.cfc.challan.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;

@Repository
public class ChallanBankDetailDAO extends AbstractDAO<BankMasterEntity> implements IChallanBankDetailDAO {

    @Override
    public Map<Long, String> getBankList(final Organisation organisation) {
        final Map<Long, String> bankList = new LinkedHashMap<>();
        final StringBuilder stringBuilder = new StringBuilder(
                "select b.bankId,b.bank,b.branch,e.baAccountNo from "
                        + ",BankMasterEntity b,BankAccountMasterEntity e   where "
                        + " e.orgId= ?1 and b.bankId=e.bankId.bankId and e.appChallanFlag='Y' ");
        final Query query = createQuery(stringBuilder.toString());
        query.setParameter(1, organisation.getOrgid());
        @SuppressWarnings("unchecked")
        final List<Object[]> bankMasters = query.getResultList();
        if ((bankMasters != null) && !bankMasters.isEmpty()) {
            for (final Object[] bankMaster : bankMasters) {
                bankList.put(Long.valueOf(bankMaster[3].toString()),
                        bankMaster[1].toString() + ">> " + bankMaster[2].toString() + ">> " + bankMaster[3].toString());
            }
            return bankList;
        }
        return null;
    }

}
