--liquibase formatted sql
--changeset nilima:V20190703183010_AL_tb_ac_budgetory_estimate1.sql
alter table tb_ac_budgetory_estimate change column `FI04_N1` FIELD_ID decimal(15,0) DEFAULT NULL COMMENT 'Additional number FI04_N1 to be used in future';