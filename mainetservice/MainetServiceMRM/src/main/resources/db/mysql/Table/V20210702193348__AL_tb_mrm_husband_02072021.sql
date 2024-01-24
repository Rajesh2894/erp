--liquibase formatted sql
--changeset Kanchan:V20210702193348__AL_tb_mrm_husband_02072021.sql
alter table tb_mrm_husband add column CASTE5 bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702193348__AL_tb_mrm_husband_020720211.sql
alter table tb_mrm_husband_hist add column CASTE5 bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702193348__AL_tb_mrm_husband_020720212.sql
alter table tb_mrm_wife add column CASTE5 bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702193348__AL_tb_mrm_husband_020720213.sql
alter table tb_mrm_wife_hist add column CASTE5 bigint(12) NULL;
