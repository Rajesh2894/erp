--liquibase formatted sql
--changeset Anil:V20200309122640__AL_tb_wt_penalty_09032020.sql
ALTER TABLE tb_wt_penalty ADD COLUMN BM_IDNO BIGINT(12) NOT NULL AFTER ACTUAL_ARREAR_AMOUNT;
