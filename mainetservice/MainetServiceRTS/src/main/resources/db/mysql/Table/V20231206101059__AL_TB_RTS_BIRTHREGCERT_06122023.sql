--liquibase formatted sql
--changeset PramodPatil:V20231206101059__AL_TB_RTS_BIRTHREGCERT_06122023.sql
ALTER TABLE TB_RTS_BIRTHREGCERT ADD COLUMN BIRTH_PTINNO BIGINT(15) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231206101059__AL_TB_RTS_BIRTHREGCERT_061220231.sql
ALTER TABLE tb_rts_deathregcert ADD COLUMN DEATH_PTINNO BIGINT(15) null default null;
