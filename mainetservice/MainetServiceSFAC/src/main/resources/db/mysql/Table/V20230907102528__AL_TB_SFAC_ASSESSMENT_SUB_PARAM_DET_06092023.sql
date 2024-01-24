--liquibase formatted sql
--changeset PramodPatil:V20230907102528__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_06092023.sql
alter table TB_SFAC_ASSESSMENT_SUB_PARAM_DET add column INPUT_FIELD_VAL Decimal (15,2) null default '0.00';