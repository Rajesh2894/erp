--liquibase formatted sql
--changeset Kanchan:V20220419161537__AL_TB_RL_PROPERTY_DTL_19042022.sql
alter table TB_RL_PROPERTY_DTL add column PROP_AREA_METER Decimal(12,2) NULL DEFAULT NULL;
