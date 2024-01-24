--liquibase formatted sql
--changeset Anil:V20200409104530__AL_tb_services_mst_09042020.sql
ALTER TABLE tb_services_mst ADD COLUMN CDM_CHILD_DEPT_ID BIGINT(12) NOT NULL ;
--liquibase formatted sql
--changeset Anil:V20200409104530__AL_tb_services_mst_090420201.sql
ALTER TABLE tb_services_mst_hist ADD COLUMN CDM_CHILD_DEPT_ID BIGINT(12) NOT NULL;
