--liquibase formatted sql
--changeset PramodPatil:V20230823111115_AL_TB_AS_PRO_DETAIL_HIST_23082023.sql
alter table TB_AS_PRO_DETAIL_HIST add column PRO_FACTOR_VAL varchar (15) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20230823111115_AL_TB_AS_PRO_DETAIL_HIST_230820231.sql
alter table TB_AS_ASSESMENT_DETAIL_HIST add column MN_FACTOR_VAL varchar (15) null default null;