package com.abm.mainet.mrm.dao;

import java.util.Date;
import java.util.List;

public interface IMarriageRegistrationDAO {

    void updateMarriageRegData(Long marId, String status, String urlParam, Long orgId, Long applicationId, Long updatedBy,
            Date updatedDate, String actionStatus);

    List<Object[]> searchMarriageData(Date marriageDate, Date appDate, String status, String serialNo, Long orgId, Long husbandId,
            Long wifeId);

    void updateAppointmentData(Long marId, Long pageNo);

    public List<Object[]> searchAppointmentData(Date appointmentDate, Long orgId);

    void updateAppointmentResc(Long appointmentId, Date appointmentDate, Date appointmentTime, Long orgId, Long updatedBy,
            Date updatedDate);

}
