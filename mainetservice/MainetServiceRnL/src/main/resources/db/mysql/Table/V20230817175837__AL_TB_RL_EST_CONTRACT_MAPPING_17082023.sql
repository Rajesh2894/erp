--liquibase formatted sql
--changeset PramodPatil:V20230817175837__AL_TB_RL_EST_CONTRACT_MAPPING_17082023.sql
alter table TB_RL_EST_CONTRACT_MAPPING add column TYPES_AFFECTED bigint(18) null default null;