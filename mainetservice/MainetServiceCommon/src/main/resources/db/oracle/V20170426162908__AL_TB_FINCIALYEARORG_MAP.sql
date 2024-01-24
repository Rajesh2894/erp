--liquibase formatted sql
--changeset nilima:V20170426162908__AL_TB_FINCIALYEARORG_MAP.sql
alter table TB_FINCIALYEARORG_MAP add fa_frommonth number(3);
alter table TB_FINCIALYEARORG_MAP add fa_tomonth number(3);
alter table TB_FINCIALYEARORG_MAP add fa_fromyear number(4);
alter table TB_FINCIALYEARORG_MAP add fa_toyear number(4);
alter table TB_FINCIALYEARORG_MAP add fa_monstatus NUMBER(12);
alter table TB_FINCIALYEARORG_MAP add fa_yearstatus NUMBER(12); 
comment on column TB_FINCIALYEARORG_MAP.fa_frommonth
  is 'Fiscale From  Month';
comment on column TB_FINCIALYEARORG_MAP.fa_tomonth
  is 'Fiscale To  Month';
comment on column TB_FINCIALYEARORG_MAP.fa_fromyear
  is 'Fiscale From Year';
comment on column TB_FINCIALYEARORG_MAP.fa_toyear
  is 'Fiscale To Year';
comment on column TB_FINCIALYEARORG_MAP.fa_monstatus
  is 'Fiscale "SOFT" close';
comment on column TB_FINCIALYEARORG_MAP.fa_yearstatus
  is 'Fiscale "Hard" close';
alter table TB_FINCIALYEARORG_MAP drop column ya_type_cpd_id;
alter table TB_FINCIALYEARORG_MAP drop column lang_id;
comment on column TB_FINCIALYEARORG_MAP.map_id
  is 'primary Key';
comment on column TB_FINCIALYEARORG_MAP.fa_yearid
  is 'Foregin Key (TB_FINANCIALYEAR)';
comment on column TB_FINCIALYEARORG_MAP.remark
  is 'Remark';
