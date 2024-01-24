--liquibase formatted sql
--changeset nilima:V20181211180505__AL_tb_sw_constdemo_garbagecoll_20112018.sql
ALTER TABLE tb_sw_constdemo_garbagecoll 
ADD COLUMN VE_NO VARCHAR(15) NULL AFTER VE_VETYPE;


