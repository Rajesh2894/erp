--liquibase formatted sql
--changeset Kanchan:V20221229173812__AL_Tb_SFAC_Cbbo_Master_29122022.sql
Alter table Tb_SFAC_Cbbo_Master
add column DMC_APPROVAL bigint(20) Null default null,
add column APPL_PENDING_DATE datetime Null default null;