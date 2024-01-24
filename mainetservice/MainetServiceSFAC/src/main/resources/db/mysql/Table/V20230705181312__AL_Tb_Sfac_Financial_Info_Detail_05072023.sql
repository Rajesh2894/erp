--liquibase formatted sql
--changeset Kanchan:V20230705181312__AL_Tb_Sfac_Financial_Info_Detail_05072023.sql
alter table Tb_Sfac_Financial_Info_Detail
add column BUSINESS_ACTIVITY_CAT1 BigInt(20) null default null AFTER REVENUE,
add column BUSINESS_ACTIVITY_CAT2 BigInt(20) null default null AFTER BUSINESS_ACTIVITY_CAT1;