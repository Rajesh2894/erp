--liquibase formatted sql
--changeset Kanchan:V20230220110604__AL_tb_sfac_equity_grant_det_20022023.sql
Alter table tb_sfac_equity_grant_det Add Column AADHAAR_NO bigint(20) Null default null;