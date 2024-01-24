--liquibase formatted sql
--changeset nilima:V20171012122317__al_ser_tb_organisation_09102017.sql
ALTER TABLE tb_organisation
CHANGE COLUMN O_LOGO O_LOGO VARCHAR(200) NULL DEFAULT NULL COMMENT '' ;
