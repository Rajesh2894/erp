--liquibase formatted sql
--changeset nilima:V20180609170638__al_tb_sw_route_mast.sql
ALTER TABLE tb_sw_route_mast 
CHANGE COLUMN RO_NO RO_NO VARCHAR(50) NOT NULL COMMENT 'Route Number' ,
CHANGE COLUMN RO_NAME RO_NAME VARCHAR(100) NOT NULL COMMENT 'Route Name' ,
CHANGE COLUMN RO_NAME_REG RO_NAME_REG VARCHAR(45) COMMENT 'Route Name Regional' ,
CHANGE COLUMN RO_START_POINT RO_START_POINT BIGINT(12) NOT NULL COMMENT 'Starting Point' ,
CHANGE COLUMN RO_END_POINT RO_END_POINT BIGINT(12) NOT NULL COMMENT 'End Point' ,
CHANGE COLUMN RO_DISTANCE RO_DISTANCE DECIMAL(12,2) NOT NULL COMMENT 'Total Route Distance' ,
CHANGE COLUMN RO_DISTANCE_UNIT RO_DISTANCE_UNIT BIGINT(12) NOT NULL COMMENT 'Total Route Distance Unit' ,
CHANGE COLUMN RO_VE_TYPE RO_VE_TYPE BIGINT(12) NOT NULL COMMENT 'Vechicle Type' ,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT 'organization id' ,
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL ,
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date' ,
CHANGE COLUMN LG_IP_MAC LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record' ,
ADD COLUMN  DE_ID BIGINT(12) NOT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST' AFTER RO_VE_TYPE,
ADD COLUMN RO_DIST_DES DECIMAL(12,2) NOT NULL COMMENT 'Distance from Disposal Site' AFTER  DE_ID,
ADD COLUMN RO_DIST_DES_UNIT DECIMAL(12,2) NOT NULL COMMENT 'unit for Disposal Site' AFTER RO_DIST_DES;
