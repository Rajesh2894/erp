--liquibase formatted sql
--changeset nilima:V20170426152835__AL_TB_FINANCIALYEAR.sql
alter table TB_FINANCIALYEAR drop column lang_id;
alter table TB_FINANCIALYEAR modify created_by NUMBER(12);
alter table TB_FINANCIALYEAR modify updated_by NUMBER(12);
alter table TB_FINANCIALYEAR add lg_ip_mac varchar2(100);
alter table TB_FINANCIALYEAR add lg_ip_mac_upd varchar2(100);
comment on column TB_FINANCIALYEAR.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_FINANCIALYEAR.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';

alter table TB_FINANCIALYEAR add fa_frommonth number(3);
alter table TB_FINANCIALYEAR add fa_tomonth number(3);
alter table TB_FINANCIALYEAR add fa_fromyear number(4);
alter table TB_FINANCIALYEAR add fa_toyear number(4);
alter table TB_FINANCIALYEAR add fa_monstatus NUMBER(12);
alter table TB_FINANCIALYEAR add fa_yearstatus NUMBER(12); 
comment on column TB_FINANCIALYEAR.fa_frommonth
  is 'Fiscale From  Month';
comment on column TB_FINANCIALYEAR.fa_tomonth
  is 'Fiscale To  Month';
comment on column TB_FINANCIALYEAR.fa_fromyear
  is 'Fiscale From Year';
comment on column TB_FINANCIALYEAR.fa_toyear
  is 'Fiscale To Year';
comment on column TB_FINANCIALYEAR.fa_monstatus
  is 'Fiscale "SOFT" close';
comment on column TB_FINANCIALYEAR.fa_yearstatus
  is 'Fiscale "Hard" close';
