--liquibase formatted sql
--changeset Kanchan:V20230106183516__AL_Tb_SFAC_Fpo_Master_Det_06012023.sql
Alter table Tb_SFAC_Fpo_Master_Det
add column PRIMARY_SECONDARY_CROP bigint(20) Null default null,
add column APPROVED_BY_DMC bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230106183516__AL_Tb_SFAC_Fpo_Master_Det_060120231.sql
Alter table Tb_SFAC_Fpo_Master add column APPL_PENDING_DATE datetime Null default null;