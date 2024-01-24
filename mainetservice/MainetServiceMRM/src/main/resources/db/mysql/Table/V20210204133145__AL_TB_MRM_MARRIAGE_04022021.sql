--liquibase formatted sql
--changeset Kanchan:V20210204133145__AL_TB_MRM_MARRIAGE_04022021.sql
alter table TB_MRM_MARRIAGE add column
 WARD1   bigint(12)     NULL,
add WARD2   bigint(12)     NULL,
add WARD3   bigint(12)     NULL,
add WARD4   bigint(12)     NULL,
add WARD5   bigint(12)     NULL;
--liquibase formatted sql
--changeset Kanchan:V20210204133145__AL_TB_MRM_MARRIAGE_040220211.sql
alter table TB_MRM_MARRIAGE_HIST add column
  WARD1   bigint(12)     NULL,
add WARD2   bigint(12)     NULL,
add WARD3   bigint(12)     NULL,
add WARD4   bigint(12)     NULL,
add WARD5   bigint(12)     NULL;

