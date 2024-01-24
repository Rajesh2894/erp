--liquibase formatted sql
--changeset Kanchan:V20220921113857__AL_TB_RL_PROPERTY_DTL_21092022.sql
alter table TB_RL_PROPERTY_DTL
add column PROP_LENGTH decimal(12,2) null default null,
add column PROP_BREADTH decimal(12,2) null default null;