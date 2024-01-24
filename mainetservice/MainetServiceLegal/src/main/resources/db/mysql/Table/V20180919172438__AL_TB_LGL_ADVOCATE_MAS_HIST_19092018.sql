--liquibase formatted sql
--changeset nilima:V20180919172438__AL_TB_LGL_ADVOCATE_MAS_HIST_19092018.sql
ALTER TABLE TB_LGL_ADVOCATE_MAS_HIST 
ADD COLUMN adv_feetype CHAR(1) NULL AFTER adv_maritalstatus,
ADD COLUMN adv_feeamt DECIMAL(15,2) NULL AFTER adv_feetype;