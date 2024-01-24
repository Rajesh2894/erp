--liquibase formatted sql
--changeset Kanchan:V20210623100143__AL_tb_csmr_info_23062021.sql
alter table tb_csmr_info add column MOBILENO_OTP varchar(10);
--liquibase formatted sql
--changeset Kanchan:V20210623100143__AL_tb_csmr_info_230620211.sql
alter table tb_csmr_info_hist add column MOBILENO_OTP varchar(10);
