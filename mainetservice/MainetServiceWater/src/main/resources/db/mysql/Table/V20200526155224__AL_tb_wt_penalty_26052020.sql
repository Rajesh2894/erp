--liquibase formatted sql
--changeset Anil:V20200526155224__AL_tb_wt_penalty_26052020.sql
ALTER TABLE tb_wt_penalty CHANGE COLUMN CS_CCN CS_IDN VARCHAR(40) NULL DEFAULT NULL ;

