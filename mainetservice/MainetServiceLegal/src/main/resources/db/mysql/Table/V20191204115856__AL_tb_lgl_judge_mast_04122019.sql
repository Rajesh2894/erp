--liquibase formatted sql
--changeset Anil:V20191204115856__AL_tb_lgl_judge_mast_04122019.sql
ALTER TABLE tb_lgl_judge_mast CHANGE COLUMN JUDGE_GENDER JUDGE_GENDER BIGINT(12) NULL COMMENT 'Gender';
