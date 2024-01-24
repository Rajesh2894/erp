create table TB_AS_PRO_ASSESMENT_FACTOR_DTL
( pro_assf_id           NUMBER(12) not null,
  pro_assd_id           NUMBER(12) not null,
  pro_assf_factor       NUMBER(12) not null,
  pro_assf_factor_id    NUMBER(12) not null,
  pro_assf_factor_value NUMBER(12) not null,
  pro_assf_active       CHAR(1) default 'Y' not null,
  orgid             NUMBER(12) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100));
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.pro_assf_id
  is 'primary key';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.pro_assd_id
  is 'Foregin Key(TB_AS_ASSESMENT_DETAIL)';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.pro_assf_factor
  is 'Factors (PREFIX ''FCT'')';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.pro_assf_factor_id
  is 'Store CPM ID of prefixes present in ''FCT'' prefix';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.pro_assf_factor_value
  is 'Factors value';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.pro_assf_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.orgid
  is 'orgnisation id';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.created_by
  is 'user id who created the record';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.created_date
  is 'record creation date';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.updated_by
  is 'user id who update the data';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_PRO_ASSESMENT_FACTOR_DTL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_AS_PRO_ASSESMENT_FACTOR_DTL
  add constraint PK_PRO_ASSF_ID primary key (PRO_ASSF_ID)
  using index;
alter table TB_AS_PRO_ASSESMENT_FACTOR_DTL
  add constraint FK_PRO_ASSF_ASSD_ID foreign key (PRO_ASSD_ID)
  references TB_AS_PRO_ASSESMENT_DETAIL (PRO_ASSD_ID);
