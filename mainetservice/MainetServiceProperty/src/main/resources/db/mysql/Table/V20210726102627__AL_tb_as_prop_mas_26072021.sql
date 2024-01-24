--liquibase formatted sql
--changeset Kanchan:V20210726102627__AL_tb_as_prop_mas_26072021.sql
alter table tb_as_prop_mas add column LOGICAL_PROP_NO VARCHAR(50) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726102627__AL_tb_as_prop_mas_260720211.sql
alter table tb_as_prop_mas add column ADDRESS_REG VARCHAR(1000) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210726102627__AL_tb_as_prop_mas_260720212.sql
alter table tb_as_prop_mas add column AREA_NAME_REG VARCHAR(100) NULL DEFAULT NULL;


