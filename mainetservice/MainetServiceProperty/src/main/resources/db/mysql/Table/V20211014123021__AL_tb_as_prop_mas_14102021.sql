--liquibase formatted sql
--changeset Kanchan:V20211014123021__AL_tb_as_prop_mas_14102021.sql
ALTER TABLE tb_as_prop_mas  ADD COLUMN PM_SERVICE_STATUS bigint(20) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20211014123021__AL_tb_as_prop_mas_141020211.sql
ALTER TABLE tb_as_prop_mas  ADD COLUMN BILL_MET_CNG_FLAG VARCHAR(10) NULL default NULL;


