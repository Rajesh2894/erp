--liquibase formatted sql
--changeset Kanchan:V20220110183220__CR_tb_transfer_duty_scheduling_10012022.sql
CREATE TABLE `tb_transfer_duty_scheduling` (
  `TRANSFER_ID` bigint(12) NOT NULL,
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL COMMENT 'ID No',
  `CONT_STAFF_NAME` varchar(1000) DEFAULT NULL COMMENT 'Name',
  `EMP_TYPE_ID` bigint(12) DEFAULT NULL COMMENT 'Employee Type ID',
  `DAY_PREFIX_ID` bigint(20) DEFAULT NULL,
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL COMMENT 'Schedule From',
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL COMMENT 'Schedule To',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Current Shift',
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'Current Location',
  `REMARKS` varchar(150) DEFAULT NULL,
  `REASON_TRANSFER` varchar(150) DEFAULT NULL,
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  PRIMARY KEY (`TRANSFER_ID`)
) ;
--liquibase formatted sql
--changeset Kanchan:V20220110183220__CR_tb_transfer_duty_scheduling_100120221.sql
CREATE TABLE `tb_transfer_duty_scheduling_hist` (
  `TRANSFER_ID_HIS` bigint(12) NOT NULL,
  `TRANSFER_ID` bigint(12) NOT NULL,
  `CONT_STAFF_ID_NO` varchar(45) DEFAULT NULL COMMENT 'ID No',
  `CONT_STAFF_NAME` varchar(1000) DEFAULT NULL COMMENT 'Name',
  `EMP_TYPE_ID` bigint(12) DEFAULT NULL COMMENT 'Employee Type ID',
  `DAY_PREFIX_ID` bigint(20) DEFAULT NULL COMMENT 'Weekly Off',
  `CONT_STAFF_SCH_FROM` datetime DEFAULT NULL COMMENT 'Schedule From',
  `CONT_STAFF_SCH_TO` datetime DEFAULT NULL COMMENT 'Schedule To',
  `CPD_SHIFT_ID` bigint(12) DEFAULT NULL COMMENT 'Current Shift',
  `LOC_ID` bigint(12) DEFAULT NULL COMMENT 'Current Location',
  `REMARKS` varchar(150) DEFAULT NULL,
  `REASON_TRANSFER` varchar(150) DEFAULT NULL,
  `ORGID` bigint(19) NOT NULL COMMENT 'Organisation Id',
  `CREATED_BY` bigint(12) DEFAULT NULL COMMENT 'User Id',
  `CREATED_DATE` datetime NOT NULL COMMENT 'Creation Date',
  `LG_IP_MAC` varchar(100) DEFAULT NULL COMMENT 'Client Machine Login Name | IP Address | Physical Address',
  `UPDATED_BY` bigint(19) DEFAULT NULL COMMENT 'Updated By',
  `UPDATED_DATE` datetime DEFAULT NULL COMMENT 'Updated Date',
  `LG_IP_MAC_UPD` varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine Login Name | IP Address | Physical Address',
  PRIMARY KEY (`TRANSFER_ID_HIS`)
);
