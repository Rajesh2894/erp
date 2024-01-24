--liquibase formatted sql
--changeset nilima:V20170426162845__AL_TB_FINANCIALYEAR.sql
alter table TB_FINANCIALYEAR drop column lang_id;
alter table TB_FINANCIALYEAR modify created_by NUMBER(12);
alter table TB_FINANCIALYEAR modify updated_by NUMBER(12);
alter table TB_FINANCIALYEAR add lg_ip_mac varchar2(100);
alter table TB_FINANCIALYEAR add lg_ip_mac_upd varchar2(100);
comment on column TB_FINANCIALYEAR.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_FINANCIALYEAR.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
