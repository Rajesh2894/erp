--liquibase formatted sql
--changeset nilima:V20181129205808__CR_TB_SW_ITC_CDWM_29112018.sql
CREATE TABLE TB_SW_ITC_CDWM (
  ITCWM_ID bigint(12) NOT NULL,
  ITCWM_TRANDATE date ,
  VE_VETYPE bigint(12) ,
  VE_NO varchar(15),
  ITCWM_LATE_ARRIVALS varchar(200) ,
  ITCWM_NO_ARRIVALS varchar(200) ,
  ITCWM_CD_WASTE char(1) ,
  ITCWM_CDWASTE_AREANAME varchar(200) ,
  ITCWM_UNIFORM char(1) ,
  ITCWM_BEHCUR char(1) ,
  ITCWM_MOBILE_APPUSAGE char(1) ,
  ITCWM_TRANSPORT_PROCESSING char(1) ,
  ITCWM_CDM_WASTE char(1) ,
  ORGID bigint(12) ,
  CREATED_BY bigint(12) ,
  CREATED_DATE datetime ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (ITCWM_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
