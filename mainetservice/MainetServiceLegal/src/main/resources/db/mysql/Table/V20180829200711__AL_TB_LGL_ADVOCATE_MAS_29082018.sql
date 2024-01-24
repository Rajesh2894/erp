--liquibase formatted sql
--changeset nilima:V20180829200711__AL_TB_LGL_ADVOCATE_MAS_29082018.sql
ALTER TABLE TB_LGL_ADVOCATE_MAS 
ADD COLUMN adv_feetype CHAR(1) NULL COMMENT 'C->Per Case,L->per lawyer' AFTER adv_maritalstatus,
ADD COLUMN adv_feeamt DECIMAL(15,2) NULL COMMENT 'Fee Amount' AFTER adv_feetype;
