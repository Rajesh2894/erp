--liquibase formatted sql
--changeset nilima:V20181101180341__CR_TB_SW_EMPLOYEE_31102018.sql
CREATE TABLE tb_sw_employee (
  SW_EMPID bigint(12) NOT NULL COMMENT 'Primary Key',
  MRF_ID bigint(12) NOT NULL COMMENT 'MRF Center Id',
  DSGID bigint(12) DEFAULT NULL COMMENT 'Designation',
  CPD_TTL_ID bigint(12) NOT NULL COMMENT 'Title',
  SW_EMPNAME varchar(200) NOT NULL COMMENT 'Employee Name',
  SW_EMPMNAME varchar(200) DEFAULT NULL COMMENT 'Employee Middle Name',
  SW_EMPLNAME varchar(200) NOT NULL COMMENT 'Employee Last Name',
  SW_EMPGENDER varchar(1) NOT NULL COMMENT 'Gender',
  SW_EMPMOBNO varchar(30) NOT NULL COMMENT 'Mobile No',
  SW_EMPEMAIL varchar(100) DEFAULT NULL COMMENT 'Email id',
  SW_EMP_ADDRESS varchar(100) NOT NULL COMMENT 'Address',
  SW_EMP_ADDRESS1 varchar(100) DEFAULT NULL COMMENT 'Address1',
  SW_EMPPINCODE varchar(11) NOT NULL COMMENT 'Pincode',
  SW_EMPUID bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SW_EMPID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181101180341__CR_TB_SW_EMPLOYEE_311020181.sql
CREATE TABLE TB_SW_EMPLOYEE_HIST(
  SW_EMPID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  SW_EMPID bigint(12) NOT NULL COMMENT 'Primary Key',
  MRF_ID bigint(12) NOT NULL COMMENT 'MRF Center Id',
  DSGID bigint(12) DEFAULT NULL COMMENT 'Designation',
  CPD_TTL_ID bigint(12) NOT NULL COMMENT 'Title',
  SW_EMPNAME varchar(200) NOT NULL COMMENT 'Employee Name',
  SW_EMPMNAME varchar(200) DEFAULT NULL COMMENT 'Employee Middle Name',
  SW_EMPLNAME varchar(200) NOT NULL COMMENT 'Employee Last Name',
  SW_EMPGENDER varchar(1) NOT NULL COMMENT 'Gender',
  SW_EMPMOBNO varchar(30) NOT NULL COMMENT 'Mobile No',
  SW_EMPEMAIL varchar(100) DEFAULT NULL COMMENT 'Email id',
  SW_EMP_ADDRESS varchar(100) NOT NULL COMMENT 'Address',
  SW_EMP_ADDRESS1 varchar(100) DEFAULT NULL COMMENT 'Address1',
  SW_EMPPINCODE varchar(11) NOT NULL COMMENT 'Pincode',
  SW_EMPUID bigint(12) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SW_EMPID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



