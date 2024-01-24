--liquibase formatted sql
--changeset Kanchan:V20210309114625__AL_TB_ML_TRADE_MAST_09032021.sql
alter table TB_ML_TRADE_MAST add column  TRD_CANCEL_REASON Varchar(1000),add TRD_CANCEL_BY Varchar(50),add CANCEL_DATE datetime;
