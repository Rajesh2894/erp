-- Create table
create table TB_AS_ASSESMENT_FACTOR_DETAIL
(
  assf_id           NUMBER(12) not null,
  assd_id           NUMBER(12) not null,
  assf_factor       NUMBER(12) not null,
  assf_factor_id    NUMBER(12) not null,
  assf_factor_value NUMBER(12) not null,
  assf_active       CHAR(1) default 'Y' not null,
  orgid             NUMBER(12) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_id
  is 'primary key';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assd_id
  is 'Foregin Key(TB_AS_ASSESMENT_DETAIL)';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_factor
  is 'Factors (PREFIX ''FCT'')';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_factor_id
  is 'Store CPM ID of prefixes present in ''FCT'' prefix';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_factor_value
  is 'Factors value';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.created_date
  is 'record creation date';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_AS_ASSESMENT_FACTOR_DETAIL
  add constraint PK_ASSF_ID primary key (ASSF_ID)
  using index;
alter table TB_AS_ASSESMENT_FACTOR_DETAIL
  add constraint FK_ASSF_ASSD_ID foreign key (ASSD_ID)
  references TB_AS_ASSESMENT_DETAIL (ASSD_ID);
