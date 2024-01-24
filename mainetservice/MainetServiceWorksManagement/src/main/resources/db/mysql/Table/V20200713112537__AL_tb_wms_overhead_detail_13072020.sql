--liquibase formatted sql
--changeset Anil:V20200713112537__AL_tb_wms_overhead_detail_13072020.sql
ALTER TABLE tb_wms_overhead_detail CHANGE COLUMN WORK_ID WORK_ID BIGINT(12) NOT NULL COMMENT 'foregin key TB_WMS_WORKESTIMATE_MAS' ;

