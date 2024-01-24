--liquibase formatted sql
--changeset Kailash:V20171123173653__CR_PORTAL_TB_EIP_FAQ_hist2.sql
DROP TABLE IF EXISTS  TB_EIP_FAQ_HIST;

--liquibase formatted sql
--changeset Kailash:V20171123173653__CR_PORTAL_TB_EIP_FAQ_hist1.sql
CREATE TABLE TB_EIP_FAQ_HIST (
  FAQ_ID_H bigint(12) ,
  FAQ_ID bigint(12) ,
  ANSWER_EN varchar(500) ,
  ANSWER_REG varchar(2000) ,
  QUESTION_EN varchar(250) ,
  QUESTION_REG varchar(2000) ,
  SEQ_NO decimal(19,0) ,
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
  UNAUTHENTICATION varchar(20) ,
  H_STATUS varchar(1) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
