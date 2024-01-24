--liquibase formatted sql
--changeset Kanchan:V20210705205315__AL_tb_csmr_info_his_05062021.sql
alter table tb_csmr_info_hist modify column PM_PROP_USG_TYP varchar(100)
