--liquibase formatted sql
--changeset nilima:V20180712184001__AL_TB_OBJECTION_MAST1.sql
CREATE TABLE TB_PG_BANK_PARAMETER_DETAIL (
  PG_PRAM_DET_ID decimal(15,0) NOT NULL COMMENT 'Primary Key',
  PG_ID decimal(15,0) DEFAULT NULL COMMENT 'Pg Bank Parameter detail  Id',
  PAR_NAME varchar(1000) DEFAULT NULL COMMENT 'Bank Parameter name',
  PAR_VALUE varchar(1000) DEFAULT NULL COMMENT 'Bank Parameter Value',
  PAR_STATUS char(1) DEFAULT NULL COMMENT 'Status of the record',
  ORGID int(11) NOT NULL COMMENT 'Organisation Id',
  CREATED_BY int(11) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  LANG_ID int(11) DEFAULT NULL COMMENT 'Language Id',
  UPDATED_BY int(11) DEFAULT NULL COMMENT ' user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  COMM_N1 decimal(15,0) DEFAULT NULL COMMENT 'additional Number COMM_N1 to be used in future',
  COMM_V1 varchar(200) DEFAULT NULL COMMENT 'additional Varchar COMM_V1 to be used in future',
  COMM_D1 datetime DEFAULT NULL COMMENT 'additional Date COMM_D1 to be used in future',
  COMM_LO1 char(1) DEFAULT NULL COMMENT 'Additional Logical field COMM_LO1 to be used in future',
  PRIMARY KEY (PG_PRAM_DET_ID),
  KEY FK_PG_ID (PG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;