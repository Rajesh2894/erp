--liquibase formatted sql
--changeset nilima:V20180619212812__CR_tb_sysmodfunction_19062018.sql
ALTER TABLE tb_sysmodfunction 
ADD COLUMN SM_SHORTDESC VARCHAR(5) NULL AFTER SM_PARAM2;

--liquibase formatted sql
--changeset nilima:V20180619212812__CR_tb_sysmodfunction_190620181.sql
ALTER TABLE tb_sysmodfunction_hist 
ADD COLUMN SM_SHORTDESC VARCHAR(5) NULL AFTER SM_PARAM2;