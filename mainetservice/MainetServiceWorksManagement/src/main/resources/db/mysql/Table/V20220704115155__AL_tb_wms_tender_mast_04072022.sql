--liquibase formatted sql
--changeset Kanchan:V20220704115155__AL_tb_wms_tender_mast_04072022.sql
alter table tb_wms_tender_mast change column TND_BANKAMT ISD_AMT decimal(15,2) null default null,change column TND_SECAMT RMD_AMT decimal(15,2) null default null;