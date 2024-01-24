--liquibase formatted sql
--changeset nilima:V20180625154000__AL_tb_wms_project_mast_25062018.sql
ALTER TABLE tb_wms_project_mast
ADD COLUMN RSO_NO VARCHAR(40) NOT NULL COMMENT 'Resolution number' AFTER PROJ_ACTIVE,
ADD COLUMN RSO_DATE DATE NOT NULL default '0000-01-01' COMMENT 'Resolution Date' AFTER RSO_NO;

--liquibase formatted sql
--changeset nilima:V20180625154000__AL_tb_wms_project_mast_250620181.sql
UPDATE tb_wms_project_mast set RSO_NO=1,RSO_DATE=now();
commit;



