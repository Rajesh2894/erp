--liquibase formatted sql
--changeset PramodPatil:V20230817181557__AL_TB_SFAC_ASSESSMENT_MASTER_17082023.sql
alter table TB_SFAC_ASSESSMENT_MASTER
add column CBBO_UNIQUE_ID varchar (100) null default null,
add column ASSMNT_DATE DateTime null default null,
add column ASSMNT_PERIOD_1 BigInt(20) null default null,
add column ASSMNT_PERIOD_2 BigInt(20) null default null,
add column FPO_AGE_MORE_ONE BigInt(20) null default null,
add column FPO_AGE_MORE_TWO BigInt(20) null default null,
add column FPO_AGE_MORE_THREE BigInt(20) null default null,
add column EMPAL_IA_NAMES varchar(500) null default null,
add column IA_NAME varchar(100) null default null,
add column FPO_ALLOCATION_TARGET BigInt(20) null default null,
add column FPO_REG_COUNT BigInt(20) null default null,
add column FPO_REG_PEND_COUNT BigInt(20) null default null;