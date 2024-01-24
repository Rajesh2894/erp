--liquibase formatted sql
--changeset PramodPatil:V20231211183413__AL_tb_sfac_credit_info_detail_11122023.sql
alter table tb_sfac_credit_info_detail add column CG_COV_AVAIL bigint(20) null;

--liquibase formatted sql
--changeset PramodPatil:V20231211183413__AL_tb_sfac_credit_info_detail_111220231.sql
alter table tb_sfac_credit_info_detail_hist add column CG_COV_AVAIL bigint(20) null;

--liquibase formatted sql
--changeset PramodPatil:V20231211183413__AL_tb_sfac_credit_info_detail_111220232.sql
alter table tb_sfac_credit_info_detail add column DATE_OF_APP datetime null;

--liquibase formatted sql
--changeset PramodPatil:V20231211183413__AL_tb_sfac_credit_info_detail_111220233.sql
alter table tb_sfac_credit_info_detail_hist add column DATE_OF_APP datetime null;