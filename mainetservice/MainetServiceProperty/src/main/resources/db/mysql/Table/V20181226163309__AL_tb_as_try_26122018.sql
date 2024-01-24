--liquibase formatted sql
--changeset nilima:V20181226163309__AL_tb_as_try_26122018.sql
ALTER TABLE tb_as_try 
ADD COLUMN TRY_VSRNO VARCHAR(50) NULL AFTER TRY_PROP_TYPE;
