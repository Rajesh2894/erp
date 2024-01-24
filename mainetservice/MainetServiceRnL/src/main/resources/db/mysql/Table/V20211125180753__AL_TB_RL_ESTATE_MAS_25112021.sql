--liquibase formatted sql
--changeset Kanchan:V20211125180753__AL_TB_RL_ESTATE_MAS_25112021.sql
alter table TB_RL_ESTATE_MAS modify column ES_SURVEY_NO varchar(50) NULL DEFAULT NULL;

