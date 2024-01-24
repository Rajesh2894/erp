--liquibase formatted sql
--changeset Kanchan:V20230705195920__AL_tb_sfac_fpo_bank_det_05072023.sql
Alter table tb_sfac_fpo_bank_det modify column ACCOUNT_NO varchar(18) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230705195920__AL_tb_sfac_fpo_bank_det_050720231.sql
Alter table TB_SFAC_FARMER_BANK_DETAILS modify column ACCOUNT_NO varchar(18) null default null;