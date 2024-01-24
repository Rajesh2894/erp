--liquibase formatted sql
--changeset Kanchan:V20221004162749__AL_TB_RL_ESTATE_MAS_04102022.sql
alter table TB_RL_ESTATE_MAS add column RNL_GST_NO varchar(200) null default null;