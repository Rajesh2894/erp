--liquibase formatted sql
--changeset Kailash:V20171123175624__CR_PORTAL_TB_EIP_USER_CONTACT_US_hist2.sql
DROP TABLE IF EXISTS TB_EIP_USER_CONTACT_US_HIST;

--liquibase formatted sql
--changeset Kailash:V20171123175624__CR_PORTAL_TB_EIP_USER_CONTACT_US_hist1.sql
CREATE TABLE TB_EIP_USER_CONTACT_US_HIST (
  ATT_ID_H bigint(12) ,
  ATT_ID bigint(12) ,
  ATT_PATH varchar(4000) ,
  CREATED_DATE datetime ,
  DESC_QUERY varchar(1000) ,
  DMS_DOC_ID varchar(100) ,
  EMAIL_ID varchar(200) ,
  FIRST_NAME varchar(200) ,
  ISDELETED varchar(1) ,
  LANG_ID int(11) ,
  LAST_NAME varchar(200) ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  ORGID int(11) ,
  PHONE_NO bigint(12) ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  USER_ID int(11) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;