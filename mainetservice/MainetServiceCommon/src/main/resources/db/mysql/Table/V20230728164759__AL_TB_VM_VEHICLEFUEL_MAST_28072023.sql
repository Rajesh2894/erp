--liquibase formatted sql
--changeset PramodPatil:V20230728164759__AL_TB_VM_VEHICLEFUEL_MAST_28072023.sql
ALTER TABLE TB_VM_VEHICLEFUEL_MAST MODIFY VEF_DMNO varchar(25) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230728164759__AL_TB_VM_VEHICLEFUEL_MAST_280720231.sql
ALTER TABLE TB_VM_VEHICLEFUEL_MAST_HIST MODIFY VEF_DMNO varchar(25) null default null;

