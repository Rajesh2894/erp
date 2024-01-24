--liquibase formatted sql
--changeset Kanchan:V20211101190242__AL_tb_location_mas_01112021.sql
alter table tb_location_mas add column LOC_CODE  varchar(5) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101190242__AL_tb_location_mas_011120211.sql
alter table tb_location_mas_hist add column LOC_CODE  varchar(5) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211101190242__AL_tb_location_mas_011120212.sql
alter table TB_RL_ESTATE_MAS add column PURPOSE bigint(14),add ACQ_TYPE bigint(14),add HOLDING_TYPE bigint(14) DEFAULT NULL;
