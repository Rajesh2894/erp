--liquibase formatted sql
--changeset PramodPatil:V20230912142544__AL_tb_as_assesment_mast_12092023.sql
alter table  tb_as_assesment_mast add column BUILDING_PERMISSION char(1);
alter table  tb_as_assesment_mast add column BUILDING_PERMISSION_NO varchar(200);

--liquibase formatted sql
--changeset PramodPatil:V20230912142544__AL_tb_as_assesment_mast_120920231.sql
alter table  tb_as_pro_assesment_mast add column BUILDING_PERMISSION char(1);
alter table  tb_as_pro_assesment_mast add column BUILDING_PERMISSION_NO varchar(200);