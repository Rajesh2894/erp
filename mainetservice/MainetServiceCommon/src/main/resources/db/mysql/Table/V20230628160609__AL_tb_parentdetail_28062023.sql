--liquibase formatted sql
--changeset Kanchan:V20230628160609__AL_tb_parentdetail_28062023.sql
ALTER TABLE tb_parentdetail DROP FOREIGN KEY FK_PD_CPD_F_EDUCN_ID;