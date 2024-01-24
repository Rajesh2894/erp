--liquibase formatted sql
--changeset Kanchan:V20220216192849__AL_tb_hearing_mas_16022022.sql
alter table tb_hearing_mas  modify column HR_ATT_NAME   varchar(100) DEFAULT NULL;
