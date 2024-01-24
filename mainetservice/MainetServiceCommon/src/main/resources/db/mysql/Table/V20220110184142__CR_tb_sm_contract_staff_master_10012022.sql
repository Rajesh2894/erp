--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_10012022.sql
CREATE TABLE `tb_sm_contract_staff_master` (
  `CONT_STSFF_ID` bigint(12) NOT NULL,
  `CONT_STAFF_ADDRESS` varchar(2000) DEFAULT NULL COMMENT 'Address',
  `CONT_STAFF_NAME` varchar(1000) DEFAULT NULL COMMENT 'Name',
  `CONT_STAFF_MOB` varchar(30) DEFAULT NULL COMMENT 'Mobile No',
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL COMMENT 'ID No',
  `CONT_STAFF_APPOINT_DATE` datetime DEFAULT NULL COMMENT 'Appointment Date',
  `DSG_ID` bigint(12) DEFAULT NULL COMMENT 'Designation',
  `VENDOR_ID` bigint(12) DEFAULT NULL COMMENT 'Agency',
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'Current Location',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Current Shift',
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL COMMENT 'Schedule From',
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL COMMENT 'Schedule To',
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  `DAY_PREFIX_ID` bigint(20) DEFAULT NULL,
  `STATUS` varchar(2) NOT NULL,
  `SEX` varchar(2) NOT NULL,
  `DOB` datetime DEFAULT NULL,
  `EMP_TYPE_FLAG` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`CONT_STSFF_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_100120221.sql
CREATE TABLE `tb_sm_daily_incident_reg` (
  `INCIDENT_ID` bigint(12) NOT NULL,
  `INCIDENT_DATE` datetime NOT NULL COMMENT 'Incident Date',
  `INCIDENT_TIME` time NOT NULL,
  `INCIDENT_REMARKS` varchar(150) NOT NULL COMMENT 'Remarks',
  `VISITING_OFFICER` varchar(150) NOT NULL,
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime DEFAULT NULL COMMENT 'Created Date',
  `UPDATED_BY` bigint(10) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'client machine''s login name | ip address | physical address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine is Login Name | IP Address | Physical Address',
  `ORGID` bigint(12) DEFAULT NULL COMMENT 'Additional nvarchar2 FI04_V1 to be used in future',
  PRIMARY KEY (`INCIDENT_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_100120222.sql
CREATE TABLE `tb_sm_deployment_of_staff` (
  `DEPL_ID` bigint(12) NOT NULL,
  `EMP_TYPE_ID` bigint(12) DEFAULT NULL COMMENT 'Employee Type ID',
  `VENDOR_ID` bigint(12) DEFAULT NULL,
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL COMMENT 'ID No',
  `CONT_STAFF_MOB` varchar(30) DEFAULT NULL COMMENT 'Mobile No',
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'To Location',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'To Shift',
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL COMMENT 'Schedule From',
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL COMMENT 'Schedule To',
  `REMARKS` varchar(150) DEFAULT NULL COMMENT 'Remarks',
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  `DEPL_SEQ` varchar(25) DEFAULT NULL,
  `WF_STATUS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DEPL_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_100120223.sql
CREATE TABLE `tb_sm_deployment_of_staff_his` (
  `H_DEPL_ID` bigint(12) NOT NULL,
  `DEPL_ID` bigint(12) NOT NULL,
  `EMP_TYPE_ID` bigint(12) DEFAULT NULL COMMENT 'Employee Type ID',
  `VENDOR_ID` bigint(12) DEFAULT NULL,
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL COMMENT 'ID No',
  `CONT_STAFF_MOB` varchar(30) DEFAULT NULL COMMENT 'Mobile No',
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'To Location',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'To Shift',
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL COMMENT 'Schedule From',
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL COMMENT 'Schedule To',
  `REMARKS` varchar(150) DEFAULT NULL COMMENT 'Remarks',
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  PRIMARY KEY (`H_DEPL_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_100120224.sql
CREATE TABLE `tb_sm_petrol_requisition` (
  `request_Id` bigint(12) NOT NULL COMMENT 'primary key',
  `date` date DEFAULT NULL COMMENT 'Petrol Requisition date',
  `time` time NOT NULL COMMENT 'Petrol Requisition time',
  `vehicle_No` varchar(20) NOT NULL COMMENT 'Petrol Requisition Vehicle number',
  `vehicle_Type` bigint(12) NOT NULL COMMENT 'Petrol Requisition Vehicle Type',
  `fuel_Type` bigint(12) NOT NULL COMMENT 'Petrol Requisition Fuel Type',
  `request_Status` varchar(15) DEFAULT NULL COMMENT 'Petrol Requisition approved or rejected or closed by hod',
  `fuel_Quantity` decimal(10,2) NOT NULL COMMENT 'Petrol Requisition Fuel Quantity',
  `dept_id` bigint(12) NOT NULL COMMENT 'Petrol Requisition Department',
  `vehicle_driver` bigint(12) NOT NULL COMMENT 'Petrol Requisition Vehicle driver',
  `orgid` bigint(12) NOT NULL COMMENT 'organization id',
  `created_by` bigint(12) NOT NULL COMMENT 'login user id',
  `created_date` datetime NOT NULL COMMENT 'login user date',
  `updated_by` bigint(12) DEFAULT NULL COMMENT 'login user id after modify record',
  `updated_date` datetime DEFAULT NULL COMMENT 'login user date after modify record',
  `lg_ip_mac` varchar(100) NOT NULL COMMENT 'login user machine id',
  `lg_ip_mac_upd` varchar(100) DEFAULT NULL COMMENT 'login user machine id after modify record',
  `WF_STATUS` varchar(20) DEFAULT NULL,
  `Fuel_Request_No` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`request_Id`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_100120225.sql
CREATE TABLE `tb_sm_shift_master` (
  `SHIFT_MAS_ID` bigint(12) NOT NULL,
  `SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Shift',
  `SHIFT_DESC` varchar(2000) DEFAULT NULL COMMENT 'Shift description',
  `FROM_TIME` time DEFAULT NULL COMMENT 'From Time',
  `TO_TIME` time DEFAULT NULL COMMENT 'To Time',
  `IS_CROSS_DAY_SHIFT` varchar(2) DEFAULT NULL,
  `IS_GENERAL_SHIFT` varchar(2) DEFAULT NULL,
  `STATUS` varchar(2) DEFAULT NULL,
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  PRIMARY KEY (`SHIFT_MAS_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110184142__CR_tb_sm_contract_staff_master_100120226.sql
CREATE TABLE `tb_sm_vehicle_log_book` (
  `ve_ID` bigint(12) NOT NULL COMMENT 'Primary Key',
  `OUT_DATE` date NOT NULL COMMENT 'vehicle OUT date',
  `IN_DATE` date DEFAULT NULL COMMENT 'vehicle IN date',
  `ve_Out_Time` time NOT NULL COMMENT 'vehicle out time',
  `ve_In_Time` time DEFAULT NULL COMMENT 'VEHICLE iN TIME',
  `ve_Journey_From` varchar(120) NOT NULL COMMENT 'VEHICLE JOURNEY FROM',
  `ve_Journey_To` varchar(120) DEFAULT NULL COMMENT 'VEHICLE JOURNEY To',
  `day_End_Meter_Read` double(20,5) DEFAULT NULL COMMENT 'day start meter reading.',
  `day_Start_Meter_Read` double(20,5) NOT NULL COMMENT 'day start meter reading.',
  `fuel_In_Litre` double(15,5) DEFAULT NULL COMMENT 'fuel in litre',
  `date_Of_Fueling` datetime DEFAULT NULL COMMENT 'date of fueling',
  `day_Visit_Desc` varchar(300) NOT NULL COMMENT 'Description',
  `driver_Id` bigint(12) NOT NULL COMMENT 'Driver Name',
  `typeOfVehicle` varchar(100) DEFAULT NULL COMMENT 'type of vehicle',
  `veNo` varchar(100) NOT NULL COMMENT 'Vehicle Number',
  `reason` varchar(300) NOT NULL COMMENT 'reason',
  `ORGID` bigint(12) NOT NULL COMMENT 'Organization ID',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'Login User ID',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Login User Date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'Login User ID after modify record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Login User Date after modify record',
  `LG_IP_MAC` varchar(100) NOT NULL COMMENT 'Login User Machine ID',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Login User Machine ID after modify record',
  PRIMARY KEY (`ve_ID`)
) ;
