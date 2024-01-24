--liquibase formatted sql
--changeset PramodPatil:V20231109171320__AL_Tb_Sfac_Equity_Grant_Mast_09112023.sql
alter table Tb_Sfac_Equity_Grant_Mast modify column BUSINESS_OF_FPC varchar(500) null default null;
