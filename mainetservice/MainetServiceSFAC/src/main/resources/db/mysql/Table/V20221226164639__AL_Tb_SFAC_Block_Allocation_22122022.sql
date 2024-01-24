--liquibase formatted sql
--changeset Kanchan:V20221226164639__AL_Tb_SFAC_Block_Allocation_22122022.sql
Alter table Tb_SFAC_Block_Allocation drop column CBBO_UNIQUE_ID;
--liquibase formatted sql
--changeset Kanchan:V20221226164639__AL_Tb_SFAC_Block_Allocation_221220221.sql
Alter table Tb_SFAC_Block_Allocation modify column ALLOCATION_YEAR_ID bigint(20) null default null;