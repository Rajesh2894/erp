package com.abm.mainet.common.master.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.BankMasterEntity;

@Repository
public class BankMasterDaoImpl extends AbstractDAO<BankMasterEntity> implements BankMasterDao {

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<BankMasterEntity> findByAllGridSearchData(final String bank, final String branch, final String ifsc,
            final String micr, final String city) {

        String queryString = "select bm from BankMasterEntity bm where bm.bankId is not null";

        if ((bank != null) && !bank.isEmpty()) {
            queryString += " and bm.bank =:bank";
        }
        if ((branch != null) && !branch.isEmpty()) {
            queryString += " and bm.branch =:branch";
        }
        if ((ifsc != null) && !ifsc.isEmpty()) {
            queryString += " and bm.ifsc =:ifsc";
        }
        if ((micr != null) && !micr.isEmpty()) {
            queryString += " and bm.micr =:micr";
        }
        if ((city != null) && !city.isEmpty()) {
            queryString += " and bm.city =:city";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        if ((bank != null) && !bank.isEmpty()) {
            query.setParameter("bank",
                    bank);
        }
        if ((branch != null) && !branch.isEmpty()) {
            query.setParameter("branch",
                    branch);
        }
        if ((ifsc != null) && !ifsc.isEmpty()) {
            query.setParameter("ifsc",
                    ifsc);
        }
        if ((micr != null) && !micr.isEmpty()) {
            query.setParameter("micr",
                    micr);
        }
        if ((city != null) && !city.isEmpty()) {
            query.setParameter("city",
                    city);
        }
        List<BankMasterEntity> result = null;
        result = query.getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public BankMasterEntity isCombinationIfscCodeExists(final String ifsc) {

        String queryString = "select te from BankMasterEntity te where";

        if ((ifsc == null) || ifsc.isEmpty()) {
            queryString += " te.ifsc is null";
        } else {
            queryString += " te.ifsc =:ifsc";
        }

        final Query query = createQuery(queryString);

        if ((ifsc != null) && !ifsc.isEmpty()) {
            query.setParameter("ifsc",
                    ifsc);
        }

        final List<BankMasterEntity> result = query.getResultList();
        if ((result != null) && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public BankMasterEntity isCombinationBranchNameExists(final String bank, final String branch) {

        String queryString = "select te from BankMasterEntity te where te.bankId is not null";

        if ((bank == null) || bank.isEmpty()) {
            queryString += " and te.bank is null";
        } else {
            queryString += " and te.bank =:bank";
        }
        if ((branch == null) || branch.isEmpty()) {
            queryString += " and te.branch is null";
        } else {
            queryString += " and te.branch =:branch";
        }

        final Query query = createQuery(queryString);

        if ((bank != null) && !bank.isEmpty()) {
            query.setParameter("bank",
                    bank);
        }
        if ((branch != null) && !branch.isEmpty()) {
            query.setParameter("branch",
                    branch);
        }

        final List<BankMasterEntity> result = query.getResultList();
        if ((result != null) && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<BankMasterEntity> getBankList() {

        final Query query = createQuery("select bk from BankMasterEntity bk");
        @SuppressWarnings("unchecked")
        final List<BankMasterEntity> bankDetails = query.getResultList();
        return bankDetails;

    }

    @Override
    @Transactional
    public List<Object[]> getChequeDDNoBankAccountNames(final Long bankAccountId) {

        final Query query = createQuery(
                "select bk.bankId, bk.bank from BankMasterEntity bk where bk.bankId=:bankAccountId order by 1 desc");
        query.setParameter("bankAccountId",
                bankAccountId);
        @SuppressWarnings("unchecked")
        final List<Object[]> bankDetails = query.getResultList();
        return bankDetails;

    }

    @SuppressWarnings("unchecked")
    @Override
    public BankMasterEntity isDuplicateMICR(final String micr) {
        final String queryString = "select bankMst from BankMasterEntity bankMst where bankMst.micr=:micr";
        final Query query = createQuery(queryString);
        query.setParameter("micr", micr);

        final List<BankMasterEntity> result = query.getResultList();
        if ((result != null) && !result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

	@Override
	public List<BankMasterEntity> getBankListByName(String bankName) {
		  final Query query = createQuery("select bk from BankMasterEntity bk where bk.bank =:bank");
		  query.setParameter("bank", bankName);
	        @SuppressWarnings("unchecked")
	        final List<BankMasterEntity> bankDetails = query.getResultList();
	        return bankDetails;
	}

	@Override
	public List<Object[]> getBankListGroupBy() {

        final Query query = createQuery("select bk.bank from BankMasterEntity bk group by bk.bank");
        @SuppressWarnings("unchecked")
        final List<Object[]> bankDetails = query.getResultList();
        return bankDetails;

    }

	@Override
	public String getBankBranch(Long bankId) {


        final Query query = createQuery("select bk from BankMasterEntity bk where bk.bankId =:bankId");
        query.setParameter("bankId", bankId);
        @SuppressWarnings("unchecked")
        final List<BankMasterEntity> result = query.getResultList();
        if ((result != null) && !result.isEmpty()) {
            return result.get(0).getBranch();
        } else {
            return null;
        }
       
	}

}
