package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abm.mainet.common.domain.CitizenDashboardView;

/**
 * @author ritesh.patil
 *
 */
public interface CitizenDashBoardRepository extends JpaRepository<CitizenDashboardView, Long> {

    @Query("select ca from CitizenDashboardView ca where ca.empId =?1 and ca.orgId=?2 order by ca.apmApplicationDate DESC")
    List<CitizenDashboardView> getAllApplicationsOfCitizen(Long empId, Long orgId);

    @Query( "select c.smServiceName, c.smServiceNameMar , c.smShortdesc , d.dpDeptdesc, "
    		+ "d.dpNameMar, b.tranCmId ,b.referenceId, b.referenceDate, b.recvStatus "
    		+ "from PaymentTransactionMas b, ServiceMaster c, Department d "
    		+ "where b.sendPhone=?1 and b.orgId=?2 and b.smServiceId=c.smServiceId and c.tbDepartment.dpDeptid=d.dpDeptid "
    		+ "and (b.referenceId,b.referenceDate) in (select x.referenceId,max(x.referenceDate) "
    		+ "from PaymentTransactionMas x group by referenceId) and b.recvStatus<>'success'")
    List<Object[]> getAllFaliuredOrCancelledOnlineList(String mobNo, Long orgId);

    @Query( "select c.smServiceName, c.smServiceNameMar , "
    		+ "b.referenceId ,b.sendFirstname ,b.sendEmail ,b.sendPhone ,b.sendAmount ,b.smServiceId,b.feeIds "
    		+ "from PaymentTransactionMas b, ServiceMaster c "
    		+ "where b.tranCmId=?1 and b.smServiceId=c.smServiceId ")
    Object[] getPayPendingDataByONLTransId(Long onlTransNo);

}
