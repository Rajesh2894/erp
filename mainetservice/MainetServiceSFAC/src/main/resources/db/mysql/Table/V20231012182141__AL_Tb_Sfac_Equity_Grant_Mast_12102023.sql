--liquibase formatted sql
--changeset PramodPatil:V20231012182141__AL_Tb_Sfac_Equity_Grant_Mast_12102023.sql
Alter table Tb_Sfac_Equity_Grant_Mast
add column fm_ref_id varchar(50) null default null,
add column fm_amt_app_dt datetime null default null ,
add column fm_amt_approved decimal(15,2) null default null;