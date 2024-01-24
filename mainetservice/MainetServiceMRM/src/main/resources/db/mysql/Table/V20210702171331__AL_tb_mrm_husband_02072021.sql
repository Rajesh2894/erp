--liquibase formatted sql
--changeset Kanchan:V20210702171331__AL_tb_mrm_husband_02072021.sql
alter table tb_mrm_husband add column CASTE1 bigint(12),add CASTE2 bigint(12),add CASTE3 bigint(12),add CASTE4 bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702171331__AL_tb_mrm_husband_020720211.sql
alter table tb_mrm_husband_hist add column CASTE1 bigint(12),add CASTE2 bigint(12),add CASTE3 bigint(12),add CASTE4 bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702171331__AL_tb_mrm_husband_020720212.sql
alter table tb_mrm_wife add column CASTE1 bigint(12),add CASTE2 bigint(12),add CASTE3 bigint(12),add CASTE4 bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702171331__AL_tb_mrm_husband_020720213.sql
alter table tb_mrm_wife_hist add column CASTE1 bigint(12),add CASTE2 bigint(12),add CASTE3 bigint(12),add CASTE4 bigint(12) NULL;
