--liquibase formatted sql
--changeset PramodPatil:V20231108142437__MO_tb_bd_deathreg_corr_08112023.sql
ALTER TABLE tb_bd_deathreg_corr MODIFY dr_sex varchar(10) not null;

--liquibase formatted sql
--changeset PramodPatil:V20231108142437__MO_tb_bd_deathreg_corr_081120231.sql
ALTER TABLE tb_bd_deathreg_corr_history MODIFY dr_sex varchar(10) not null;