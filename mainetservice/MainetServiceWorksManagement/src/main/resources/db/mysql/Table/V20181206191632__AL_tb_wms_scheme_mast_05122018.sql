--liquibase formatted sql
--changeset nilima:V20181206191632__AL_tb_wms_scheme_mast_05122018.sql
ALTER TABLE tb_wms_scheme_mast 
CHANGE COLUMN SCH_CODE SCH_CODE VARCHAR(10) NULL COMMENT 'Scheme Code' ,
CHANGE COLUMN SCH_DESCRIPTION SCH_DESCRIPTION VARCHAR(1000) NULL COMMENT 'Scheme Description' ,
CHANGE COLUMN SCH_START_DATE SCH_START_DATE DATE NULL COMMENT 'Scheme Start Date' ;
