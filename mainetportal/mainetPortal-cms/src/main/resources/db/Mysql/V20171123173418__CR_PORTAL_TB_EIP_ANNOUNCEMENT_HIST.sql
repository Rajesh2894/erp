--liquibase formatted sql
--changeset Kailash:V20171123173418__CR_PORTAL_TB_EIP_ANNOUNCEMENT_HIST2.sql
drop table TB_EIP_ANNOUNCEMENT_HIST;

--liquibase formatted sql
--changeset Kailash:V20171123173418__CR_PORTAL_TB_EIP_ANNOUNCEMENT_HIST1.sql
CREATE TABLE TB_EIP_ANNOUNCEMENT_HIST (
  ANNOUNCE_ID_H bigint(12) ,
  ANNOUNCE_ID bigint(12) ,
  ANNOUNCE_DESC_ENG varchar(4000) ,
  ANNOUNCE_DESC_REG varchar(4000) ,
  ATTACH varchar(200) ,
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
  DMS_DOC_ID varchar(100) ,
  DMS_FOLDER_PATH varchar(100) ,
  DMS_DOC_NAME varchar(100) ,
  DMS_DOC_VERSION varchar(10) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
