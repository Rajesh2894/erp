--liquibase formatted sql
--changeset Kailash:V20171123175200__CR_PORTAL_TB_EIP_LINKS_MASTER_hist1.sql
CREATE TABLE TB_EIP_LINKS_MASTER_HIST (
  LINK_ID_H bigint(12) ,
  LINK_ID bigint(12) ,
  LINK_PATH varchar(250) ,
  LINK_IMAGE_NAME varchar(100) ,
  LINK_ORDER decimal(10,2) ,
  LINK_TITLE_EN varchar(500) ,
  LINK_TITLE_REG varchar(2000) ,
  CPD_SECTION bigint(12) ,
  ISDELETED varchar(1) ,
  ORGID bigint(12) ,
  CREATED_BY bigint(12) ,
  CREATED_DATE datetime ,
  UPDATED_BY int(11) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  IS_LINK_MODIFY varchar(2) ,
  CHEKER_FLAG varchar(1) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
