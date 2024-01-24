--liquibase formatted sql
--changeset Kanchan:V20210323153232__AL_tb_counterschedule_23032021.sql
alter table tb_counterschedule add column cs_status varchar(1) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210323153232__AL_tb_counterschedule_230320211.sql
alter table tb_counterschedule_hist add column cs_status varchar(1) NOT NULL;
