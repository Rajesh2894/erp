--liquibase formatted sql
--changeset Kanchan:V20221125190938__AL_Tb_SFAC_Block_Allocation_25112022.sql
Alter table Tb_SFAC_Block_Allocation add column AUTH_REMARK varchar(100) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221125190938__AL_Tb_SFAC_Block_Allocation_251120221.sql
Alter table Tb_SFAC_Block_Allocation_Hist add column AUTH_REMARK varchar(100) DEFAULT NULL;