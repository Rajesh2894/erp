--liquibase formatted sql
--changeset Anil:V20200410173159__AL_tb_services_mst_10042020.sql
ALTER TABLE tb_services_mst CHANGE COLUMN CDM_CHILD_DEPT_ID CDM_CHILD_DEPT_ID BIGINT(12) NULL ;
--liquibase formatted sql
--changeset Anil:V20200410173159__AL_tb_services_mst_100420201.sql
ALTER TABLE tb_services_mst_hist CHANGE COLUMN CDM_CHILD_DEPT_ID CDM_CHILD_DEPT_ID BIGINT(12) NULL ;


