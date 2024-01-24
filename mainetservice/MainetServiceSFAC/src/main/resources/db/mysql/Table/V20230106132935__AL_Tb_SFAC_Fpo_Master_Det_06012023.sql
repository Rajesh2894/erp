--liquibase formatted sql
--changeset Kanchan:V20230106132935__AL_Tb_SFAC_Fpo_Master_Det_06012023.sql
Alter table Tb_SFAC_Fpo_Master_Det drop column FPO_FOCUS_CROP_PRI ,
drop column FPO_FOCUS_CROP_SEC ,
drop column SPECIAL_CROP ,
drop column PRIMARY_CROP_APP_BY_DMC,
drop column SECONDARY_CROP_APP_BY_DMC;