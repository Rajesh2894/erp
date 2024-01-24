create table TB_AS_PRO_ASSESMENT_OWNER_DTL
( pro_asso_id          BIGINT not null,
  pro_ass_id           BIGINT not null,
  pro_asso_owner_name  NVARCHAR(500) not null,
  pro_asso_gender      BIGINT,
  pro_asso_fathus_name NVARCHAR(500) not null,
  pro_asso_mobileno    NVARCHAR(20) not null,
  pro_asso_addharno    BIGINT not null,
  pro_asso_panno       VARCHAR(10),
  pro_asso_type        CHAR(1) not null,
  pro_asso_otype       CHAR(1),
  pro_asso_active      CHAR(1) default 'Y' not null,
  orgid            BIGINT not null,
  created_by       INT not null,
  created_date     DATETIME not null,
  updated_by       INT,
  updated_date     DATETIME,
  lg_ip_mac        VARCHAR(100),
  lg_ip_mac_upd    VARCHAR(100));

alter table TB_AS_PRO_ASSESMENT_OWNER_DTL
  add constraint PK_pro_asso_ID primary key (pro_asso_ID);
alter table TB_AS_PRO_ASSESMENT_OWNER_DTL
  add constraint FK_pro_asso_ASS_ID foreign key (PRO_ASS_ID)
  references TB_AS_PRO_ASSESMENT_MAST (PRO_ASS_ID);


comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_id
  is 'primary key';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_ass_id
  is 'foregin key Foregin Key(TB_AS_ASSESMENT_MAST)';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_owner_name
  is 'Owner Name/Company Trust Name';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_gender
  is 'Owner Gender (prefix ''GEN'')';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_fathus_name
  is 'Owner Husband Father Name/Contact Person Name';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_mobileno
  is 'Owner/Company Mobile No';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_addharno
  is 'Owner Addhar  No';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_panno
  is 'Company Pan No.';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_type
  is 'O->Owner  T->Other';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_otype
  is 'P->Primary Owner S->Secondary Owner';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.pro_asso_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.orgid
  is 'orgnisation id';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.created_by
  is 'user id who created the record';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.created_date
  is 'record creation date';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.updated_by
  is 'user id who update the data';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_PRO_ASSESMENT_OWNER_DTL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
