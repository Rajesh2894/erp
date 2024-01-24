--liquibase formatted sql
--changeset nilima:V20180518114626__CR_TB_SW_SANITATION_MAST_15052018.sql
CREATE TABLE TB_SW_SANITATION_MAST (
  SAN_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  ASSET_ID BIGINT(12) COMMENT 'ASSET_ID' ,
  SAN_TYPE BIGINT(12) NOT NULL COMMENT 'Public Toilet Type',
  SAN_NAME VARCHAR(200) NOT NULL COMMENT 'Public Toilet Name',
  COD_WARD1 BIGINT(12) NOT NULL COMMENT 'zone',
  COD_WARD2 BIGINT(12) COMMENT 'ward',
  COD_WARD3 BIGINT(12) COMMENT 'road/street',
  COD_WARD4 BIGINT(12) COMMENT 'colony/society/complex',
  COD_WARD5 BIGINT(12),
  SAN_SEAT_CNT VARCHAR(45) COMMENT 'seat count',
  SAN_GISNO VARCHAR(50) COMMENT 'gis no' ,
  LATTIUDE VARCHAR(100) COMMENT 'LATTIUDE',
  LONGITUDE VARCHAR(100) COMMENT 'LONGITUDE',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME COMMENT 'date on which updated the record' ,
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SAN_ID));
