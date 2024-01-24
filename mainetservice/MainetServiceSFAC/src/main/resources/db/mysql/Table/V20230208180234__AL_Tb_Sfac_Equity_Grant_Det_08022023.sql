--liquibase formatted sql
--changeset Kanchan:V20230208180234__AL_Tb_Sfac_Equity_Grant_Det_08022023.sql
Alter table Tb_Sfac_Equity_Grant_Det ADD Column IS_BOM varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230208180234__AL_Tb_Sfac_Equity_Grant_Det_080220231.sql
Alter table Tb_Sfac_Equity_Grant_Det modify Column TENURE bigint(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230208180234__AL_Tb_Sfac_Equity_Grant_Det_080220232.sql
Alter table Tb_Sfac_FPOProfile_Training_Info_Detail Add Column TRAINING_NAME varchar(200) NULL DEFAULT NULL;