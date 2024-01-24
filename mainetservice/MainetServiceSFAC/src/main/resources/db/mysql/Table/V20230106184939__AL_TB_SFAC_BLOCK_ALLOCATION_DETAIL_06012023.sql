--liquibase formatted sql
--changeset Kanchan:V20230106184939__AL_TB_SFAC_BLOCK_ALLOCATION_DETAIL_06012023.sql
Alter table TB_SFAC_BLOCK_ALLOCATION_DETAIL add column ALLOCATION_YEAR_TO_CBBO bigint(20) Null default null;