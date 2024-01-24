package com.abm.mainet.mrm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.mrm.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("Select ap from Appointment ap  where ap.appointmentDate =:date AND ap.orgId =:orgId")
    List<Appointment> findByAppointmentDateAndOrg(@Param("date") Date date, @Param("orgId") Long orgId);

    @Modifying
    @Query("UPDATE Appointment ap SET ap.appointmentDate = :appointmentDate,ap.appointmentTime = :appointmentTime, ap.updatedBy =:updatedBy, "
            + "ap.lgIpMacUpd =:lgIpMacUpd, ap.updatedDate = CURRENT_TIMESTAMP where ap.orgId =:orgId AND ap.appointmentId in (:appointmentIds)")
    void updateAppointmentRescByIds(@Param("appointmentDate") Date appointmentDate,
            @Param("appointmentTime") Date appointmentTime, @Param("appointmentIds") List<Long> appointmentIds,
            @Param("orgId") Long orgId, @Param("updatedBy") Long updatedBy, @Param("lgIpMacUpd") String lgIpMacUpd);

    @Query("select ap from Appointment ap JOIN FETCH ap.marId where ap.appointmentId=:appointmentId")
    Appointment findByAppointmentId(@Param("appointmentId") Long appointmentId);

}
