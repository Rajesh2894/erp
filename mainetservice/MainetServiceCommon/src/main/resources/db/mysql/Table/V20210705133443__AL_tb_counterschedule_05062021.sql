--liquibase formatted sql
--changeset Kanchan:V20210705133443__AL_tb_counterschedule_05062021.sql
alter table tb_counterschedule add column cs_frequency_sts varchar(3) NotNull;
