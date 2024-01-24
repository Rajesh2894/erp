--liquibase formatted sql
--changeset PramodPatil:V20231110155857__MO_tb_deathreg_10112023.sql
ALTER TABLE tb_deathreg MODIFY dr_sex varchar(10) not null;

--liquibase formatted sql
--changeset PramodPatil:V20231110155857__MO_tb_deathreg_101120231.sql
ALTER TABLE tb_deathreg_draft MODIFY dr_sex varchar(10) not null;

--liquibase formatted sql
--changeset PramodPatil:V20231110155857__MO_tb_deathreg_101120232.sql
ALTER TABLE tb_deathreg_history MODIFY dr_sex varchar(10) not null;