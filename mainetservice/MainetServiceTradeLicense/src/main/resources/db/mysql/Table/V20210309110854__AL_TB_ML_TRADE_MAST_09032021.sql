--liquibase formatted sql
--changeset Kanchan:V20210309110854__AL_TB_ML_TRADE_MAST_09032021.sql
Alter table TB_ML_TRADE_MAST
add column Trans_Mode_TYPE bigint(12);
