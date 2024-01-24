--liquibase formatted sql
--changeset PramodPatil:V20230803184527__AL_TB_AS_PRO_ASSESMENT_DETAIL_03082023.sql
alter table TB_AS_PRO_ASSESMENT_DETAIL add column PRO_FACTOR_VAL varchar(15) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230803184527__AL_TB_AS_PRO_ASSESMENT_DETAIL_030820231.sql
alter table TB_AS_ASSESMENT_DETAIL add column MN_FACTOR_VAL varchar(15) null default null;

