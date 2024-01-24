--liquibase formatted sql
--changeset Kanchan:V20221216184648__AL_TB_SFAC_IA_Master_16122022.sql
Alter table TB_SFAC_IA_Master add column DEPTID bigint(20) Null default null;
