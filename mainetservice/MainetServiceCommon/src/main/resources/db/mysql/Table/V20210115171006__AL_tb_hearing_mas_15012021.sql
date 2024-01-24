--liquibase formatted sql
--changeset Kanchan:V20210115171006__AL_tb_hearing_mas_15012021.sql
alter table tb_hearing_mas  add Column HR_DECISION bigint(12) NULL;





