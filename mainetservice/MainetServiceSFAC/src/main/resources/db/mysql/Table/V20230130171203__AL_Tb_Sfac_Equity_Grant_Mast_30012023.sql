--liquibase formatted sql
--changeset Kanchan:V20230130171203__AL_Tb_Sfac_Equity_Grant_Mast_30012023.sql
Alter table Tb_Sfac_Equity_Grant_Mast
ADD column BANK_ID bigint(20) null default null,
ADD column BRANCH_EMAIL varchar(100) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230130171203__AL_Tb_Sfac_Equity_Grant_Mast_300120231.sql
Alter table Tb_Sfac_Equity_Grant_Mast ADD column APP_NO varchar(50) not null;