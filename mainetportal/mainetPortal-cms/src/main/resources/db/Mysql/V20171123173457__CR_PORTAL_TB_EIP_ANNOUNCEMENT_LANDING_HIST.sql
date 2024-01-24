--liquibase formatted sql
--changeset Kailash:V20171123173457__CR_PORTAL_TB_EIP_ANNOUNCEMENT_LANDING_HIST1.sql
CREATE TABLE TB_EIP_ANNOUNCEMENT_LANDING_HIST (
  ANNOUNCE_ID_H bigint(12) ,
  ANNOUNCE_ID bigint(12) ,
  ANNOUNCE_DESC_ENG varchar(4000) ,
  ANNOUNCE_DESC_REG varchar(4000) ,
  ATTACHMENT varchar(600) ,
  ISDELETED varchar(1) ,
  ORGID int(11) ,
  USER_ID int(11) ,
  LANG_ID int(11) ,
  CREATED_DATE datetime ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  CHEKER_FLAG varchar(1) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

