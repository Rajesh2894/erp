--liquibase formatted sql
--changeset Kanchan:V20230209171811__AL_Tb_Sfac_Equity_Grant_Mast_09022023.sql
Alter table Tb_Sfac_Equity_Grant_Mast add column MODE_OF_BOARD_FORMATN bigint(20) Null default null;