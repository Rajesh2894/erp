--liquibase formatted sql
--changeset PramodPatil:V20230929181110__AL_TB_SFAC_ASSESSMENT_SUB_PARAM_DET_29092023.sql
alter Table TB_SFAC_ASSESSMENT_SUB_PARAM_DET add Column INPUT_FIELD_VAL_2 Decimal (15,2) null default '0.00';