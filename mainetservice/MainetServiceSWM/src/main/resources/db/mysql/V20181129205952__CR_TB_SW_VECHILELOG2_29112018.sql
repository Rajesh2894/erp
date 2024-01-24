--liquibase formatted sql
--changeset nilima:V20181129205952__CR_TB_SW_VECHILELOG2_29112018.sql
CREATE TABLE TB_SW_VECHILELOG2 (
  VEL2_ID bigint(12) NOT NULL,
  VEL2_TRANDATE date comment 'Date',
  VE_VETYPE bigint(12) comment 'Vechicle Type',
  VE_NO varchar(15) comment 'Vechicle No.',
  VEL2_ARRIVALS varchar(200) comment 'Arrival_time',
  VEL2_DEPARTURE varchar(200) comment 'Departure_time',
  VEL2_G1 varchar(200),
  VEL2_G2 varchar(200),
  VEL2_G3 varchar(200),
  VEL2_G4 varchar(200),
  VEL2_G5 varchar(200),
  VEL2_G6 varchar(200),
  VEL2_G7  char(1),
  VEL2_G8  bigint(12),
  VEL2_G9  varchar(200),
  VEL2_G10 varchar(200),
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (VEL2_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
