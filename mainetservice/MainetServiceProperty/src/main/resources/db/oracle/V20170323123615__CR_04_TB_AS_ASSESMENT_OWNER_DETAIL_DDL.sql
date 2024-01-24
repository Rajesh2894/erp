create table TB_AS_ASSESMENT_OWNER_DETAIL
(
  asso_id          NUMBER(12) not null,
  ass_id           NUMBER(12) not null,
  asso_owner_name  NVARCHAR2(500) not null,
  asso_gender      NUMBER(12),
  asso_fathus_name NUMBER(15,2) not null,
  asso_mobileno    NVARCHAR2(20) not null,
  asso_addharno    NUMBER(12) not null,
  asso_panno       VARCHAR2(10),
  asso_type        CHAR(1) not null,
  asso_otype       CHAR(1),
  asso_active      CHAR(1) default 'Y' not null,
  orgid            NUMBER(12) not null,
  created_by       NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100)
);
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_id
  is 'primary key';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.ass_id
  is 'foregin key Foregin Key(TB_AS_ASSESMENT_MAST)';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_owner_name
  is 'Owner Name/Company Trust Name';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_gender
  is 'Owner Gender (prefix ''GEN'')';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_fathus_name
  is 'Owner Husband Father Name/Contact Person Name';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_mobileno
  is 'Owner/Company Mobile No';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_addharno
  is 'Owner Addhar  No';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_panno
  is 'Company Pan No.';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_type
  is 'O->Owner  T->Other';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_otype
  is 'P->Primary Owner S->Secondary Owner';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.asso_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.created_date
  is 'record creation date';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_ASSESMENT_OWNER_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_AS_ASSESMENT_OWNER_DETAIL
  add constraint PK_ASSO_ID primary key (ASSO_ID)
  using index;
alter table TB_AS_ASSESMENT_OWNER_DETAIL
  add constraint FK_ASSO_ASS_ID foreign key (ASS_ID)
  references TB_AS_ASSESMENT_MAST (ASS_ID);
