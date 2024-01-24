--liquibase formatted sql
--changeset PramodPatil:V20231213141804__AL_tb_sfac_credit_info_detail_13122023.sql
alter table tb_sfac_credit_info_detail add column CR_LOAN_AVAILED bigint(20) null;

--liquibase formatted sql
--changeset PramodPatil:V20231213141804__AL_tb_sfac_credit_info_detail_131220231.sql
alter table tb_sfac_credit_info_detail_hist add column CR_LOAN_AVAILED bigint(20) null;