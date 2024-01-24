--liquibase formatted sql
--changeset Kanchan:V20230323202004__AL_TB_CONTRACT_DETAIL_23032023.sql
alter table TB_CONTRACT_DETAIL add column CONT_BG_DATE date NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230323202004__AL_TB_CONTRACT_DETAIL_230320231.sql
alter table tb_contract_detail_hist add column CONT_BG_DATE date NULL DEFAULT NULL;