--liquibase formatted sql
--changeset PramodPatil:V20231012182244__AL_Tb_SFAC_CBBO_FILED_STAFF_DET_12102023.sql
ALTER TABLE Tb_SFAC_CBBO_FILED_STAFF_DET add column CBBO_EXPERT_DESG bigint(20) null default null;