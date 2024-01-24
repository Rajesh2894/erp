--liquibase formatted sql
--changeset Kanchan:V20230119192315__AL_TB_RTS_DRN_CON_19012023.sql
alter table TB_RTS_DRN_CON
add column RESIDENTIALHOUSE bigint(12) null default null,
add column COMMERCIALHOUSE bigint(12) null default null,
add column ROAD_TYPE bigint(12) null default null,
add column LEN_ROAD bigint(12) null default null;
