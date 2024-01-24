--liquibase formatted sql
--changeset PramodPatil:V20231121171610__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_21112023.sql
alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast
add column LOAN_AMT_REQ decimal(15,2) null default null,
add column TYPE_OF_LOAN_REQ BigInt(20) null default null,
add column PURPOSE_LOAN varchar(100) null default null;