--liquibase formatted sql
--changeset PramodPatil:V20230906175258__AL_TB_SFAC_ASSESSMENT_MASTER_06092023.sql
alter table TB_SFAC_ASSESSMENT_MASTER add column TOL_OVER_ALL_SCORE Decimal (15,2) null default '0.00';

--liquibase formatted sql
--changeset PramodPatil:V20230906175258__AL_TB_SFAC_ASSESSMENT_MASTER_060920231.sql
alter table TB_SFAC_ASSESSMENT_KEY_PARAM add column OVER_ALL_SCORE_SUB_TOL Decimal (15,2) null default '0.00';