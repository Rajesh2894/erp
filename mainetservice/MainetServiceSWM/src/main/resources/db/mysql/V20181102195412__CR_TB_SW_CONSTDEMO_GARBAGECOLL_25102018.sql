--liquibase formatted sql
--changeset nilima:V20181102195412__CR_TB_SW_CONSTDEMO_GARBAGECOLL_25102018.sql
CREATE TABLE TB_SW_CONSTDEMO_GARBAGECOLL (
  COG_ID bigint(12) NOT NULL COMMENT 'Primary key',
  APM_APPLICATION_ID decimal(16,0) NOT NULL COMMENT 'Application id',
  LOC_ID bigint(12) NOT NULL COMMENT 'Location Id',
  VE_VETYPE bigint(12) NOT NULL COMMENT 'Vechicle Type',
  VE_CAPACITY decimal(10,2) DEFAULT NULL COMMENT 'Vechicle Capacity',
  COG_NOOFTRIP bigint(12) NOT NULL,
  COG_COLL_DATE datetime NOT NULL COMMENT 'Construction Garbage Collection date',
  COG_COLL_AMT decimal(15,2) NOT NULL COMMENT 'Construction Garbage Collection amount',
  COG_TERMS_ACCEPTED varchar(1) NOT NULL COMMENT 'Construction Garbage Collection terms accepted',
  COG_BUILDING_PERMISSION char(1) DEFAULT NULL,
  COG_NIDDAN_COMPLAIN_NO varchar(50) DEFAULT NULL,
  COG_PAY_FLAG varchar(1) NOT NULL COMMENT 'Construction Garbage Collection Pay flag',
  COG_COLL_STATUS varchar(1) NOT NULL COMMENT 'Status of Construction Garbage Collection (Y->Active,N->Inactive)',
  ORGID bigint(12) NOT NULL COMMENT 'Organisation Id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record\n',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record\n',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (COG_ID),
  KEY Fk_CLOCID_idx (LOC_ID),
  CONSTRAINT Fk_CLOCID FOREIGN KEY (LOC_ID) REFERENCES tb_location_mas (LOC_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Construction Demolish Garbage Collection';


