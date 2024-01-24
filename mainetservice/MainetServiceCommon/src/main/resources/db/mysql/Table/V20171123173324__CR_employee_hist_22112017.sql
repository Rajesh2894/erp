--liquibase formatted sql
--changeset nilima:V20171123173324__CR_employee_hist_22112017.sql
drop table EMPLOYEE_HIST;

--liquibase formatted sql
--changeset nilima:V20171123173324__CR_employee_hist_221120171.sql
CREATE TABLE EMPLOYEE_HIST (
  H_EMPID bigint(12) ,
  EMPID bigint(12) ,
  EMPNAME varchar(1000) ,
  EMPOSLOGINNAME varchar(100) ,
  EMPLOGINNAME varchar(100) ,
  EMPPASSWORD varchar(100) ,
  DSGID bigint(12) ,
  LOCID bigint(12) ,
  EMPPAYROLLNUMBER varchar(20) ,
  EMPISECURITYKEY varchar(140) ,
  EMPPISERVERNAME varchar(40) ,
  ISDELETED varchar(1) ,
  SYNOYNMX double ,
  ORGID bigint(12) ,
  USER_ID bigint(12) ,
  LMODDATE datetime ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LANG_ID bigint(12) ,
  EMPEMAIL varchar(100) ,
  EMPEXPIREDT datetime ,
  EMPPHOTO longblob,
  LOCK_UNLOCK varchar(1) ,
  LOGGED_IN varchar(1) ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  EMPNEW int(11) ,
  DP_DEPTID bigint(12) ,
  EMPDOB datetime ,
  EMPMOBNO varchar(30) ,
  EMPPHONENO varchar(40) ,
  EMPUWMSOWNER varchar(1) ,
  EMPREGISTRY varchar(1) ,
  EMPRECORD varchar(1) ,
  EMPNETWORK varchar(1) ,
  EMPOUTWARD varchar(1) ,
  AUT_BY bigint(12) ,
  AUT_DATE datetime ,
  CENTRALENO varchar(100) ,
  SCANSIGNATURE varchar(2000) ,
  EMPUID varchar(28) ,
  EMPUIDDOCPATH varchar(4000) ,
  EMPPHOTOPATH varchar(4000) ,
  EMPUIDDOCNAME varchar(200) ,
  ADD_FLAG varchar(1) ,
  EMP_ADDRESS varchar(100) ,
  EMP_ADDRESS1 varchar(2000) ,
  EMPPINCODE int(11) ,
  AUTH_STATUS varchar(2) ,
  AUT_MOB char(1) ,
  CPD_TTL_ID bigint(12) ,
  EMPLNAME varchar(200) ,
  EMPMNAME varchar(200) ,
  EMPL_TYPE bigint(12) ,
  EMP_GENDER varchar(1) ,
  ISUPLOADED varchar(1) ,
  EMP_COR_ADD1 varchar(2000) ,
  EMP_COR_ADD2 varchar(2000) ,
  EMP_COR_PINCODE int(11) ,
  AUT_EMAIL char(1) ,
  EMPLOYEE_NO varchar(15) ,
  AGENCY_LOCATION varchar(500) ,
  GM_ID bigint(12) ,
  PAN_NO varchar(10) ,
  H_STATUS varchar(1) ,
  PRIMARY KEY (H_EMPID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;