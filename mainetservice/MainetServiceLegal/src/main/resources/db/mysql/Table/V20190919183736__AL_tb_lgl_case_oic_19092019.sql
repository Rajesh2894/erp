--liquibase formatted sql
--changeset Anil:V20190919183736__AL_tb_lgl_case_oic_19092019.sql
ALTER TABLE tb_lgl_case_oic CHANGE COLUMN OIC_DPDEPTID OIC_DPDEPTID VARCHAR(250) NULL DEFAULT NULL;
