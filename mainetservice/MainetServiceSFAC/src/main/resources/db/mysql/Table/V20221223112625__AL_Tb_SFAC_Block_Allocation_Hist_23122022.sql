--liquibase formatted sql
--changeset Kanchan:V20221223112625__AL_Tb_SFAC_Block_Allocation_Hist_23122022.sql
Alter table Tb_SFAC_Block_Allocation_Hist change column ORG_NAME_ID MAS_ID bigint(20) not null;