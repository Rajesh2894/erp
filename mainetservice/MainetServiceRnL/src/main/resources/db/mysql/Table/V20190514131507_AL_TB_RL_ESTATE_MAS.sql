--liquibase formatted sql
--changeset nilima:V20190514131507_AL_TB_RL_ESTATE_MAS1.sql
alter table TB_RL_ESTATE_MAS add column `ES_LATITUDE` varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset nilima:V20190514131507_AL_TB_RL_ESTATE_MAS2.sql
alter table TB_RL_ESTATE_MAS add column `ES_LONGITUDE` varchar(100) DEFAULT NULL;