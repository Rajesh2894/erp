--liquibase formatted sql
--changeset Kanchan:V20210702174859__AL_tb_mrm_witness_det_02072021.sql
alter table tb_mrm_witness_det modify column REL_WITH_COUPLE bigint(12) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210702174859__AL_tb_mrm_witness_det_020720211.sql
alter table tb_mrm_witness_det_hist modify column  REL_WITH_COUPLE bigint(12) NOT NULL;
