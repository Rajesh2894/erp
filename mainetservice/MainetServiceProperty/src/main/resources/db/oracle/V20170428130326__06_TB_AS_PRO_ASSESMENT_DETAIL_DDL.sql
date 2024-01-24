create table TB_AS_PRO_ASSESMENT_DETAIL
( pro_assd_id                NUMBER(12) not null,
  pro_ass_id                     NUMBER(12) not null,
  pro_assd_unit_type_id      NUMBER(12) not null,
  pro_assd_floor_no          NUMBER(12) not null,
  pro_assd_buildup_area      NUMBER(15,2) not null,
  pro_assd_usagetype1        NUMBER(12),
  pro_assd_usagetype2        NUMBER(12),
  pro_assd_usagetype3        NUMBER(12),
  pro_assd_constru_type      NUMBER(12),
  pro_assd_year_construction DATE not null,
  pro_assd_occupancy_type    NUMBER(12) not null,
  pro_assd_assesment_date    DATE not null,
  pro_assd_annual_rent       NUMBER(15,2),
  pro_assd_std_rate          NUMBER(15,2),
  pro_assd_alv               NUMBER(15,2),
  pro_assd_rv                NUMBER(15,2),
  pro_assd_cv                NUMBER(15,2),
  pro_assd_active            CHAR(1) default 'Y' not null,
  orgid                  NUMBER(12) not null,
  created_by             NUMBER(12) not null,
  created_date           DATE not null,
  updated_by             NUMBER(12),
  updated_date           DATE,
  lg_ip_mac              VARCHAR2(100),
  lg_ip_mac_upd          VARCHAR2(100));
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_id
  is 'primary key';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_ass_id
  is 'foregin key Foregin Key(TB_AS_PRO_ASSESMENT_MAST)';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_unit_type_id
  is 'Unit Type';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_floor_no
  is 'Floor No (Prefix ''IDE'')';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_buildup_area
  is 'Floorwise Build Up Area';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_usagetype1
  is 'Usage Type (Prefix ''USA'')';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_usagetype2
  is 'Usage SubType (Prefix ''USA'')';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_usagetype3
  is 'Usage SubType (Prefix ''USA'')';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_constru_type
  is 'Construction Type (Prefix ''CSC'')';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_year_construction
  is 'Year of Construction';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_occupancy_type
  is 'Occupancy Type (Prefix ''OCS'')';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_assesment_date
  is 'Assessment Date';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_annual_rent
  is 'Annual Rent of Unit';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_std_rate
  is 'Standard Rate';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_alv
  is 'Annual Rate Value';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_rv
  is 'Rateable Value';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_cv
  is 'Capital Value';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) . ';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.orgid
  is 'orgnisation id';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.created_by
  is 'user id who created the record';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.created_date
  is 'record creation date';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.updated_by
  is 'user id who update the data';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.updated_date
  is 'date on which data is going to update';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_AS_PRO_ASSESMENT_DETAIL.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
alter table TB_AS_PRO_ASSESMENT_DETAIL
  add constraint PK_PRO_ASSD_ID primary key (PRO_ASSD_ID)
  using index;
alter table TB_AS_PRO_ASSESMENT_DETAIL
  add constraint FK_PRO_ASS_ID foreign key (PRO_ASS_ID)
  references TB_AS_PRO_ASSESMENT_MAST (PRO_ASS_ID);
