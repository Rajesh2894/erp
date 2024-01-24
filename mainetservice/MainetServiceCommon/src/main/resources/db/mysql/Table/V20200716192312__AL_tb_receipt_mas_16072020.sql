--liquibase formatted sql
--changeset Anil:V20200716192312__AL_tb_receipt_mas_16072020.sql
ALTER TABLE tb_receipt_mas CHANGE COLUMN RM_V2 PG_REFID BIGINT(12) NULL DEFAULT NULL COMMENT 'Additional varchar RM_V2to be used in future' ;

