--liquibase formatted sql
--changeset Kanchan:V20220331193232__AL_TB_CONTRACT_MAST_31032022.sql
alter table TB_CONTRACT_MAST add column CONT_COMM__DATE datetime NULL DEFAULT NULL;
