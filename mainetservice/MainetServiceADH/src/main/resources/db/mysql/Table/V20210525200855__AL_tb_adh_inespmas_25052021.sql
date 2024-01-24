--liquibase formatted sql
--changeset Kanchan:V20210525200855__AL_tb_adh_inespmas_25052021.sql
ALTER TABLE tb_adh_inespmas
ADD COLUMN NO_OF_DAYS bigint(12),add NOTICE_GEN_FLAG char(1) NULL;



