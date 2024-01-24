--liquibase formatted sql
--changeset priya:V20180327163553__CR_TB_WMS_MILESTONE_MAST.sql
drop table if exists TB_WMS_MILESTONE_MAST;
--liquibase formatted sql
--changeset priya:V20180327163553__CR_TB_WMS_MILESTONE_MAST1.sql
CREATE TABLE TB_WMS_MILESTONE_MAST (
  MILE_ID bigint(12) NOT NULL,
  WORK_ID bigint(12) DEFAULT NULL,
  PROJ_ID bigint(12) NOT NULL,
  MILE_DESCRIPTION varchar(500) NOT NULL,
  MILE_WEIGHTAGE decimal(12,2) NOT NULL,
  MILE_STARTDATE date NOT NULL,
  MILE_ENDDATE date NOT NULL,
  MILE_PERCENTAGE decimal(6,2) DEFAULT NULL,
  MILE_TYPE char(1) NOT NULL,
  orgid bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  created_date datetime NOT NULL,
  updated_by bigint(12) DEFAULT NULL,
  updated_date datetime DEFAULT NULL,
  lg_ip_mac varchar(100) NOT NULL,
  lg_ip_mac_upd varchar(100) DEFAULT NULL,
  PRIMARY KEY (MILE_ID,PROJ_ID,MILE_DESCRIPTION,MILE_WEIGHTAGE,MILE_STARTDATE,MILE_ENDDATE,MILE_TYPE,orgid,CREATED_BY,created_date,lg_ip_mac)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


