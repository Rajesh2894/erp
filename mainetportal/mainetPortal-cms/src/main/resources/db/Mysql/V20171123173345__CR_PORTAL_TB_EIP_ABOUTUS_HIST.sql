--liquibase formatted sql
--changeset Kailash:V20171123173345__CR_PORTAL_TB_EIP_ABOUTUS_HIST2.sql
DROP TABLE IF EXISTS  TB_EIP_ABOUTUS_HIST;

--liquibase formatted sql
--changeset Kailash:V20171123173345__CR_PORTAL_TB_EIP_ABOUTUS_HIST1.sql
CREATE TABLE TB_EIP_ABOUTUS_hist (
  ABOUTUS_ID_H bigint(12) ,
  ABOUTUS_ID bigint(12) ,
  DESCRIPTION_EN varchar(3000) ,
  DESCRIPTION_REG varchar(4000) ,
  ISDELETED varchar(1) ,
  ORGID int(11) ,
  USER_ID int(11) ,
  LANG_ID int(11) ,
  CREATED_DATE datetime ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  DESCRIPTION_EN1 varchar(3000) ,
  DESCRIPTION_REG1 varchar(4000) ,
  CHEKER_FLAG varchar(1) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;