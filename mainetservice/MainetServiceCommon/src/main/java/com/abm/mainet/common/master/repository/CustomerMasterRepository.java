package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.CustomerMasterEntity;

@Repository
public interface CustomerMasterRepository extends JpaRepository<CustomerMasterEntity, Long> {

    @Query("select v from CustomerMasterEntity v where v.custMobNo =:custMobNo and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByMobileNo(@Param("custMobNo") String custMobNo, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.custUIDNo =:custUIDNo and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByUidNo(@Param("custUIDNo") Long custUIDNo, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.custTINNo =:custTINNo and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByTIN(@Param("custTINNo") String custTINNo, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.custGSTNo =:custGSTNo and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByGst(@Param("custGSTNo") String custGSTNo, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.custPANNo =:custPANNo and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByPanNo(@Param("custPANNo") String custPANNo, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.custName =:custName and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByName(@Param("custName") String custName, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.custEmailId =:custEmailId and v.orgid =:orgid")
    List<CustomerMasterEntity> getCustomerByEmail(@Param("custEmailId") String custEmailId, @Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.orgid =:orgid")
    List<CustomerMasterEntity> getAllCustomer(@Param("orgid") Long orgid);

    @Query("select v from CustomerMasterEntity v where v.orgid =:orgid and v.custRefNo =:custRefNo")
    CustomerMasterEntity getCustomerByRefNo(@Param("orgid") Long orgid, @Param("custRefNo") String custRefNo);
}
