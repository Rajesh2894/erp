--liquibase formatted sql
--changeset Kailash:V20171123174804__CR_PORTAL_TB_EIP_FEEDBACK_hist1.sql
CREATE TABLE TB_EIP_FEEDBACK_HIST  (
  FD_ID_H bigint(12) ,
  FD_ID bigint(12) ,
  FD_USER_NAME varchar(1000) ,
  EMPID bigint(12) ,
  MOBILE_NO decimal(10,0) ,
  EMAIL_ID varchar(100) ,
  FD_DETAILS varchar(4000) ,
  ISDELETED varchar(1) ,
  ORGID int(11) ,
  USER_ID int(11) ,
  LANG_ID int(11) ,
  CREATED_DATE datetime ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;