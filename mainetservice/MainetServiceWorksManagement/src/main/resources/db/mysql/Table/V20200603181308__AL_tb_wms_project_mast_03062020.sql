--liquibase formatted sql
--changeset Anil:V20200603181308__AL_tb_wms_project_mast_03062020.sql
ALTER TABLE tb_wms_project_mast
CHANGE COLUMN PROJ_START_DATE PROJ_START_DATE DATE NULL COMMENT 'Project Start Date' ,
CHANGE COLUMN RSO_NO RSO_NO VARCHAR(40) NULL COMMENT 'Resolution number' ,
CHANGE COLUMN RSO_DATE RSO_DATE DATE NULL COMMENT 'Resolution Date' ;
--liquibase formatted sql
--changeset Anil:V20200603181308__AL_tb_wms_project_mast_030620201.sql
ALTER TABLE tb_wms_project_mast_hist
CHANGE COLUMN PROJ_START_DATE PROJ_START_DATE DATE NULL COMMENT 'Project Start Date' ,
CHANGE COLUMN RSO_NO RSO_NO VARCHAR(40) NULL COMMENT 'Resolution number' ,
CHANGE COLUMN RSO_DATE RSO_DATE DATE NULL COMMENT 'Resolution Date' ;
