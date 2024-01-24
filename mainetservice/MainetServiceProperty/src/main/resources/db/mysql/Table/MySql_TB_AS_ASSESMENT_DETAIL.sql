create table TB_AS_ASSESMENT_DETAIL
( assd_id             BIGINT not null comment 'primary key',
  ass_id              BIGINT not null comment 'foregin key Foregin Key(TB_AS_ASSESMENT_MAST)',
  assd_unit_type_id   BIGINT not null comment 'Unit Type',
  assd_floor_no       BIGINT not null comment 'Floor No (Prefix ''IDE'')',
  assd_buildup_area   DECIMAL(15,2) not null comment 'Floorwise Build Up Area',
  assd_usagetype1     BIGINT comment 'Usage Type (Prefix ''USA'')',
  assd_usagetype2     BIGINT comment 'Usage SubType (Prefix ''USA'')',
  assd_usagetype3     BIGINT comment 'Usage SubType (Prefix ''USA'')',
  assd_constru_type   BIGINT comment 'Construction Type (Prefix ''CSC'')',
  assd_age            SMALLINT not null comment 'AGE',
  assd_occupancy_type BIGINT not null comment 'Occupancy Type (Prefix ''OCS'')',
  assd_assesment_date DATETIME not null comment 'Assessment Date',
  assd_annual_rent    DECIMAL(15,2) comment 'Annual Rent of Unit',
  assd_std_rate       DECIMAL(15,2) comment 'Standard Rate',
  assd_alv            DECIMAL(15,2) comment 'Annual Rate Value',
  assd_rv             DECIMAL(15,2) comment 'Rateable Value',
  assd_cv             DECIMAL(15,2) comment 'Capital Value',
  assd_active         CHAR(1) default 'Y' not null comment 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ',
  orgid               BIGINT not null comment 'orgnisation id',
  created_by          INT not null comment  'user id who created the record',
  created_date        DATETIME not null comment 'record creation date',
  updated_by          INT comment 'user id who update the data',
  updated_date        DATETIME comment 'date on which data is going to update',
  lg_ip_mac           VARCHAR(100) comment 'client machine?s login name | ip address | physical address',
  lg_ip_mac_upd       VARCHAR(100) comment 'updated client machine?s login name | ip address | physical address');
  
  alter table TB_AS_ASSESMENT_DETAIL
  add constraint PK_ASSD_ID primary key (ASSD_ID);
  
  alter table TB_AS_ASSESMENT_DETAIL
  add constraint FK_ASS_ID foreign key (ASS_ID)
  references TB_AS_ASSESMENT_MAST (ASS_ID);
