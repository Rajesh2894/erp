create table TB_AS_ASSESMENT_FACTOR_DETAIL
( assf_id           BIGINT not null comment 'primary key',
  assd_id           BIGINT not null comment 'Foregin Key(TB_AS_ASSESMENT_DETAIL)',
  assf_factor       BIGINT not null comment 'Factors (PREFIX ''FCT'')',
  assf_factor_id    BIGINT not null comment 'Store CPM ID of prefixes present in ''FCT'' prefix',
  assf_factor_value BIGINT not null comment 'Factors value',
  assf_active       CHAR(1) default 'Y' not null comment 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ',
  orgid             BIGINT not null comment 'orgnisation id' ,
  created_by        INT not null comment 'user id who created the record',
  created_date      DATETIME not null comment 'record creation date',
  updated_by        INT comment 'user id who update the data',
  updated_date      DATETIME comment 'date on which data is going to update' ,
  lg_ip_mac         VARCHAR(100) comment 'client machine?s login name | ip address | physical address' ,
  lg_ip_mac_upd     VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address');
alter table TB_AS_ASSESMENT_FACTOR_DETAIL
  add constraint PK_ASSF_ID primary key (ASSF_ID);
alter table TB_AS_ASSESMENT_FACTOR_DETAIL
  add constraint FK_ASSF_ASSD_ID foreign key (ASSD_ID)
  references TB_AS_ASSESMENT_DETAIL (ASSD_ID);