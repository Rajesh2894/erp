/**
 * 
 */
package com.abm.mainet.rnl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.rnl.domain.EstateContractMapping;
/**
 * @author divya.marshettiwar
 *
 */
@Repository
public class EstateContractMappingDaoImpl extends AbstractDAO<EstateContractMapping> implements EstateContractMappingDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchData(String contNo, String propertyContractNo, String estateName, String mobileNo, Long orgId) {
		List<Object[]> entityList = new ArrayList<>();
		final StringBuilder builder = new StringBuilder();
		builder.append("select c.contId,c.contNo,dept.dpDeptdesc,c.contDate,d.contFromDate,d.contToDate, p1.contp1Name,ven.vmVendorname,ven.vmVendoradd,ven.emailId,ven.mobileNo,d.contAmount , p2.contp2Name,dept.dpDeptcode, em.estateEntity.nameEng, em.estateEntity.nameReg, em.estatePropertyEntity.code from "
		        +" ContractMastEntity c ,ContractDetailEntity d,ContractPart1DetailEntity p1 ,ContractPart2DetailEntity p2, Department dept,TbAcVendormasterEntity ven, EstateContractMapping em"
		        +" where c.contId=d.contId and c.contId=p1.contId and c.contId=p2.contId and dept.dpDeptid=c.contDept "
		        + " and ven.vmVendorid=p2.vmVendorid and p1.contp1Type='U' and p2.contp2Type='V' and c.contActive!='N' "
		        + " and p2.contp2Primary='Y' and p1.contp1Active='Y' and p2.contvActive='Y' and em.contractMastEntity.contId=c.contId and c.orgId= :orgId ");
		
		if(contNo != null && !contNo.equals("")) {
			builder.append(" and c.contNo=:contNo");
		}
		if(propertyContractNo != null && !propertyContractNo.equals("")) {
			builder.append(" and em.estatePropertyEntity.code=:propertyContractNo");
		}
		if(estateName != null && !estateName.equals("")) {
			builder.append(" and em.estateEntity.code=:estateName");
		}
		if(mobileNo != null && !mobileNo.equals("")) {
			builder.append(" and vm.mobileNo=:mobileNo");
		}
		
		final Query query = createQuery(builder.toString());
		query.setParameter("orgId", orgId);
		
		if(contNo != null && !contNo.equals("")) {
			query.setParameter("contNo", contNo);
		}
		if(propertyContractNo != null && !propertyContractNo.equals("")) {
			query.setParameter("propertyContractNo", propertyContractNo);
		}
		if(estateName != null && !estateName.equals("")) {
			query.setParameter("estateName", estateName);
		}
		if(mobileNo != null && !mobileNo.equals("")) {
			query.setParameter("mobileNo", mobileNo);
		}
		
		entityList = (List<Object[]>) query.getResultList();
		return entityList;
	}
}
