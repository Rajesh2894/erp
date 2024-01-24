--liquibase formatted sql
--changeset Kanchan:V20220110185839__CR_tb_employee_scheduling_10012022.sql
CREATE TABLE `tb_employee_scheduling` (
  `EMPL_SCDL_ID` bigint(12) NOT NULL,
  `DEPL_ID` bigint(12) DEFAULT NULL,
  `TRANSFER_ID` bigint(12) DEFAULT NULL,
  `EMP_TYPE_ID` varchar(45) DEFAULT NULL,
  `VENDOR_ID` bigint(12) DEFAULT NULL COMMENT 'Agency',
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL,
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL,
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'Current Location',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Current Shift',
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL,
  `DAY_PREFIX_ID` bigint(20) DEFAULT NULL COMMENT 'Weekly Off',
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  `OT_DATE` datetime DEFAULT NULL,
  `OT_HRS` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`EMPL_SCDL_ID`),
  KEY `FK_tbemployeescheduling_DEPIP` (`DEPL_ID`),
  KEY `FK_tbemployeescheduling_TRANIP` (`TRANSFER_ID`),
  CONSTRAINT `FK_tbemployeescheduling_DEPIP` FOREIGN KEY (`DEPL_ID`) REFERENCES `tb_sm_deployment_of_staff` (`DEPL_ID`),
  CONSTRAINT `FK_tbemployeescheduling_TRANIP` FOREIGN KEY (`TRANSFER_ID`) REFERENCES `tb_transfer_duty_scheduling` (`TRANSFER_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110185839__CR_tb_employee_scheduling_100120221.sql
CREATE TABLE `tb_employee_scheduling_det` (
  `EMPL_SCDL_DET_ID` bigint(12) NOT NULL COMMENT 'Primary Key',
  `EMPL_SCDL_ID` bigint(12) NOT NULL,
  `EMP_TYPE_ID` bigint(12) DEFAULT NULL,
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL,
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'Current Location',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Current Shift',
  `SHIFT_DAY` varchar(12) DEFAULT NULL COMMENT 'Current Shift Day',
  `SHIFT_DATE` datetime DEFAULT NULL COMMENT 'Current Shift Date',
  `STARTIME_SHIFT` datetime NOT NULL COMMENT 'Current Shift Start Time',
  `ENDTIME_SHIFT` datetime NOT NULL COMMENT 'Current Shift End Time',
  `OT_HOUR` bigint(2) DEFAULT NULL,
  `ATT_STATUS` char(1) DEFAULT NULL,
  `ORGID` bigint(12) NOT NULL COMMENT 'organization id',
  `CREATED_BY` bigint(12) NOT NULL COMMENT 'user id who created the record',
  `CREATED_DATE` datetime NOT NULL COMMENT 'record creation date',
  `UPDATED_BY` bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'date on which updated the record',
  `LG_IP_MAC` varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  `OT_CPD_SHIFT_ID` bigint(12) DEFAULT NULL,
  PRIMARY KEY (`EMPL_SCDL_DET_ID`),
  KEY `FK_EMPL_SCDL_ID_idx` (`EMPL_SCDL_ID`),
  CONSTRAINT `FK_EMPL_SCDL_ID` FOREIGN KEY (`EMPL_SCDL_ID`) REFERENCES `tb_employee_scheduling` (`EMPL_SCDL_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110185839__CR_tb_employee_scheduling_100120222.sql
CREATE TABLE `tb_employee_scheduling_hist` (
  `H_EMPL_SCDL_ID` bigint(12) NOT NULL,
  `EMPL_SCDL_ID` bigint(12) NOT NULL,
  `EMP_TYPE_ID` varchar(45) DEFAULT NULL,
  `VENDOR_ID` bigint(12) DEFAULT NULL COMMENT 'Agency',
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL,
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL,
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'Current Location',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Current Shift',
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL,
  `DAY_PREFIX_ID` bigint(20) DEFAULT NULL COMMENT 'Weekly Off',
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  PRIMARY KEY (`H_EMPL_SCDL_ID`)
) ;



