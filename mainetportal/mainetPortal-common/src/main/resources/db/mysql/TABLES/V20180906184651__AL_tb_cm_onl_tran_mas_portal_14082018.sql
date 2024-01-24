--liquibase formatted sql
--changeset nilima:V20180906184651__AL_tb_cm_onl_tran_mas_portal_14082018.sql
ALTER TABLE tb_cm_onl_tran_mas_portal 
CHANGE COLUMN RECV_MODE RECV_MODE VARCHAR(100) NULL DEFAULT NULL COMMENT 'Recv Mode' ;
