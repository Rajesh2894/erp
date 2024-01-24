--liquibase formatted sql
--changeset Kanchan:V20221003164456__AL_TB_BLOCK_ALLOCATION_03102022.sql
Alter table TB_BLOCK_ALLOCATION add column ORG_TYPE_ID  bigint(20) NULL DEFAULT NULL;