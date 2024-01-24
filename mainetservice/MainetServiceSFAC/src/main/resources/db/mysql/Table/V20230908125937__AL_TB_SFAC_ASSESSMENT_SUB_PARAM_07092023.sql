--liquibase formatted sql
--changeset PramodPatil:V20230908125937__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_07092023.sql
alter table TB_SFAC_ASSESSMENT_SUB_PARAM add column TOTAL_MARKS BigInt(20) null default null;