create table TB_AS_ASSESMENT_OWNER_DETAIL
( asso_id          BIGINT not null comment 'primary key',
  ass_id           BIGINT not null comment 'foregin key Foregin Key(TB_AS_ASSESMENT_MAST)',
  asso_owner_name  NVARCHAR(500) not null comment 'Owner Name/Company Trust Name',
  asso_gender      BIGINT comment 'Owner Gender (prefix ''GEN'')',
  asso_fathus_name DECIMAL(15,2) not null comment 'Owner Husband Father Name/Contact Person Name',
  asso_mobileno    NVARCHAR(20) not null comment 'Owner/Company Mobile No',
  asso_addharno    BIGINT not null comment 'Owner Addhar  No',
  asso_panno       VARCHAR(10) comment 'Company Pan No.',
  asso_type        CHAR(1) not null comment 'O->Owner  T->Other',
  asso_otype       CHAR(1) comment 'P->Primary Owner S->Secondary Owner',
  asso_active      CHAR(1) default 'Y' not null comment 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ',
  orgid            BIGINT not null comment 'orgnisation id',
  created_by       INT not null comment 'user id who created the record',
  created_date     DATETIME not null comment 'record creation date',
  updated_by       INT comment 'user id who update the data',
  updated_date     DATETIME comment 'date on which data is going to update',
  lg_ip_mac        VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd    VARCHAR(100)  comment 'updated client machine?s login name | ip address | physical address');
alter table TB_AS_ASSESMENT_OWNER_DETAIL
  add constraint PK_ASSO_ID primary key (ASSO_ID);
alter table TB_AS_ASSESMENT_OWNER_DETAIL
  add constraint FK_ASSO_ASS_ID foreign key (ASS_ID)
  references TB_AS_ASSESMENT_MAST (ASS_ID);

