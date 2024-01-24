--liquibase formatted sql
--changeset nilima:V20180720201103__AL_tb_sw_finecharge_col_19072018.sql
ALTER TABLE tb_sw_finecharge_col 
ADD COLUMN FCH_TYPE BIGINT(12) NULL COMMENT 'Fine Type' AFTER EMPID,
ADD COLUMN REGISTRATION_ID BIGINT(12) NULL AFTER FCH_ID;




