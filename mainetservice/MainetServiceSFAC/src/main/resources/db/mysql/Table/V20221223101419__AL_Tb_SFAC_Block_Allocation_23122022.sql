--liquibase formatted sql
--changeset Kanchan:V20221223101419__AL_Tb_SFAC_Block_Allocation_23122022.sql
Alter table Tb_SFAC_Block_Allocation change column ORG_NAME_ID MAS_ID bigint(20) not null;