--liquibase formatted sql
--changeset nilima:V20190411181727__al_tb_objection_mast.sql
ALTER TABLE tb_objection_mast 
ADD COLUMN OBJ_TIME BIGINT(12) NULL AFTER `NOTTICE_NO`;