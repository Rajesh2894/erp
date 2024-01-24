--liquibase formatted sql
--changeset PramodPatil:V20231025180314__AL_tb_rts_deathregcert_25102023.sql
alter Table tb_rts_deathregcert add column DR_AMOUNT double not null;

--liquibase formatted sql
--changeset PramodPatil:V20231025180314__AL_tb_rts_deathregcert_251020231.sql
alter Table tb_rts_birthregcert add column BR_AMOUNT double not null;