--liquibase formatted sql
--changeset Anil:V20200603164236__AL_tb_wms_project_mast_03062020.sql
ALTER TABLE tb_wms_project_mast 
ADD COLUMN PROJ_PRD BIGINT(12) NULL,
ADD COLUMN PROJ_PRD_UNT BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200603164236__AL_tb_wms_project_mast_030620201.sql
ALTER TABLE tb_wms_project_mast_hist 
ADD COLUMN PROJ_PRD BIGINT(12) NULL,
ADD COLUMN PROJ_PRD_UNT BIGINT(12);
