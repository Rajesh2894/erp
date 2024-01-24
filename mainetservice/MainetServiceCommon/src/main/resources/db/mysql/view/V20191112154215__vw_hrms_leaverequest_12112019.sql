--liquibase formatted sql
--changeset Anil:V20191112154215__vw_hrms_leaverequest_12112019.sql
drop view if exists vw_hrms_leaverequest;
--liquibase formatted sql
--changeset Anil:V20191112154215__vw_hrms_leaverequest_121120191.sql
CREATE VIEW vw_hrms_leaverequest AS
SELECT
 a.EmployeeMaster_Id,
 a.Employee_ID,
 a.Employee_Name,
 a.Date_of_Joining, 
 a.Date_of_Retirement,
 a.Mobile_No,
 a.Email_Id,
 a.Designation_Name,
a.Reporting_UserId,
 a.Reporting_ID,
 b.Employee_Name as 'Reporting_Manager_Name',
 c.Balance,
 c.CL_Balance, 
 c.EL_Balance,
 c.HPL_Balance,
 c.PL_Balance,
 c.ML_Balance,
 c.SL_Balance,
 c.Leave_Code,
 c.Leave_Type,
 c.Min_Service_Period,
 c.Payment_Impact,
 c.ulb_code as 'ORGID'
FROM suda_hrms45.tblemployeemaster a JOIN
suda_hrms45.tblemployeemaster b on a.reporting_id=b.employeemaster_id  JOIN
suda_hrms45.tblleavetypeeligiblemaster c on a.Employee_ID=c.Employee_ID where a.active_status="Active" and a.Emp_Active =1 and a.payfixation_status='Yes'
order by a.Employee_ID;

