--liquibase formatted sql
--changeset Kanchan:V20230109125453__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_09012023.sql
Alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast
add column FPO_ID bigint(20) NOT NULL,
add column DATE_ISSUED datetime NULL DEFAULT NULL,
add column COMMODITY_NAME varchar(100) NULL DEFAULT NULL,
add column COMMODITY_QUANTITY bigint(20) NULL DEFAULT NULL,
add column LIVE_STOCK_NAME varchar(100) NULL DEFAULT NULL,
add column LIVE_STOCK_QUANTITY bigint(20) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230109125453__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_090120231.sql
alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast add key FPO_ID_fk2(FPO_ID);
--liquibase formatted sql
--changeset Kanchan:V20230109125453__AL_Tb_Sfac_Fpo_Profile_Mgmt_Mast_090120232.sql
alter table Tb_Sfac_Fpo_Profile_Mgmt_Mast add CONSTRAINT Tb_Sfac_Fpo_Profile_Mgmt_Mast_FPO_ID_fk2 FOREIGN KEY (FPO_ID) REFERENCES Tb_SFAC_Fpo_Master (FPO_ID);
