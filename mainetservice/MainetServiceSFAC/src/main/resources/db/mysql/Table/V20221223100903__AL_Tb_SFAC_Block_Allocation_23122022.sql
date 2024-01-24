--liquibase formatted sql
--changeset Kanchan:V20221223100903__AL_Tb_SFAC_Block_Allocation_23122022.sql
Alter table Tb_SFAC_Block_Allocation drop column SDB1,
drop column SDB2,
drop column SDB3,
drop column SDB4,
drop column SDB5,
drop column NO_OF_FPO_ALLOCATED,
drop column ALLOCATION_CATEGORY,
drop column CHANGED_BLOCK,
drop column COMMODITY_TYPE;