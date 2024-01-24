--liquibase formatted sql
--changeset PramodPatil:V20231110155822__MO_tb_rts_deathregcert_10112023.sql
ALTER TABLE tb_rts_deathregcert MODIFY dr_sex varchar(10) not null;

