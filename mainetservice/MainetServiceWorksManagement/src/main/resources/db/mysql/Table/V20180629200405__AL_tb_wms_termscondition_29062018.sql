--liquibase formatted sql
--changeset nilima:V20180629200405__AL_tb_wms_termscondition_29062018.sql
ALTER TABLE tb_wms_termscondition
ADD COLUMN WORK_SACNO VARCHAR(200) NULL DEFAULT NULL AFTER `REF_ID`;
