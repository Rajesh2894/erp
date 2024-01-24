--liquibase formatted sql
--changeset Kanchan:V20210326103830__AL_tb_collectionmaster_hist_26032021.sql
alter table tb_collectionmaster_hist add column cm_collnid bigInt(12) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210326103830__AL_tb_collectionmaster_hist_260320211.sql
alter table tb_counterschedule_hist add column cs_scheduleid bigInt(12) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210326103830__AL_tb_collectionmaster_hist_260320212.sql
alter table tb_countermaster_hist add column cu_counterid bigInt(12) NOT NULL;
