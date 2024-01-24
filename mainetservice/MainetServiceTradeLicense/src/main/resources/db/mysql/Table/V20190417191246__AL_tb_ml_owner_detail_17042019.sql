--liquibase formatted sql
--changeset nilima:V20190417191246__AL_tb_ml_owner_detail_17042019.sql
ALTER TABLE tb_ml_owner_detail
CHANGE COLUMN TRO_EMAILID TRO_EMAILID VARCHAR(256) NOT NULL COMMENT 'Owner Email id' ;
