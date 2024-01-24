-- Create table
create table TB_LOCATION_MAS
(
  loc_id          NUMBER(12) not null,
  loc_name_eng    NVARCHAR2(500),
  loc_name_reg    NVARCHAR2(500),
  loc_description NVARCHAR2(1000),
  loc_active      VARCHAR2(1),
  loc_address1    NVARCHAR2(350),
  loc_address2    NVARCHAR2(350),
  loc_city        NVARCHAR2(50),
  loc_dwz_id      NUMBER(12),
  loc_parentid    NUMBER(12),
  loc_source      CHAR(1),
  loc_aut_status  CHAR(1),
  loc_aut_by      NUMBER(12),
  loc_aut_date    DATE,
  orgid           NUMBER(4),
  user_id         NUMBER(12),
  lang_id         NUMBER(7),
  lmoddate        DATE,
  updated_by      NUMBER(12),
  updated_date    DATE,
  lg_ip_mac       VARCHAR2(100),
  lg_ip_mac_upd   VARCHAR2(100),
  centraleno      NVARCHAR2(50),
  aut_v1          NVARCHAR2(100),
  aut_v2          NVARCHAR2(100),
  aut_v3          NVARCHAR2(100),
  aut_v4          NVARCHAR2(100),
  aut_v5          NVARCHAR2(100),
  aut_n1          NUMBER(15),
  aut_n2          NUMBER(15),
  aut_n3          NUMBER(15),
  aut_n4          NUMBER(15),
  aut_n5          NUMBER(15),
  aut_d1          DATE,
  aut_d2          DATE,
  aut_d3          DATE,
  aut_d4          DATE,
  aut_d5          DATE
);
-- Add comments to the columns 
comment on column TB_LOCATION_MAS.loc_id
  is 'primary key (location id)';
comment on column TB_LOCATION_MAS.loc_name_eng
  is 'location name in english';
comment on column TB_LOCATION_MAS.loc_name_reg
  is 'location name in Hindi';
comment on column TB_LOCATION_MAS.loc_description
  is 'location description';
comment on column TB_LOCATION_MAS.loc_active
  is 'flag to identify whether the record is deleted or not. ''Y'' for deleted (inactive) and ''N'' for not deleted (active) record.';
comment on column TB_LOCATION_MAS.loc_address1
  is 'stores the address of the department. (not in use)';
comment on column TB_LOCATION_MAS.loc_address2
  is 'stores the address of the department. (not in use)';
comment on column TB_LOCATION_MAS.loc_city
  is 'stores the city information of department. stores the city information of department. (not in use)';
comment on column TB_LOCATION_MAS.loc_dwz_id
  is 'stores the value of ward id';
comment on column TB_LOCATION_MAS.loc_parentid
  is 'maintains parent child relation for  a particular record(depid)';
comment on column TB_LOCATION_MAS.loc_source
  is 'location source (U->ULB location,O->Other location)';
comment on column TB_LOCATION_MAS.loc_aut_status
  is 'Authorisation Status';
comment on column TB_LOCATION_MAS.loc_aut_by
  is 'Authorisation By (Empid)';
comment on column TB_LOCATION_MAS.loc_aut_date
  is 'Authorisation Date';
comment on column TB_LOCATION_MAS.orgid
  is 'orgnisation id';
comment on column TB_LOCATION_MAS.user_id
  is 'user id';
comment on column TB_LOCATION_MAS.lang_id
  is 'language id';
comment on column TB_LOCATION_MAS.lmoddate
  is 'creation date';
comment on column TB_LOCATION_MAS.updated_by
  is 'updated by';
comment on column TB_LOCATION_MAS.updated_date
  is 'updated time';
comment on column TB_LOCATION_MAS.lg_ip_mac
  is 'client machine�s login name | ip address | physical address';
comment on column TB_LOCATION_MAS.lg_ip_mac_upd
  is 'updated client machine�s login name | ip address | physical address';
comment on column TB_LOCATION_MAS.centraleno
  is 'server name,owner name';
comment on column TB_LOCATION_MAS.aut_v1
  is 'additional nvarchar2 aut_v1 to be used in future';
comment on column TB_LOCATION_MAS.aut_v2
  is 'additional nvarchar2 aut_v2 to be used in future';
comment on column TB_LOCATION_MAS.aut_v3
  is 'additional nvarchar2 aut_v3 to be used in future';
comment on column TB_LOCATION_MAS.aut_v4
  is 'additional nvarchar2 aut_v4 to be used in future';
comment on column TB_LOCATION_MAS.aut_v5
  is 'additional nvarchar2 aut_v5 to be used in future';
comment on column TB_LOCATION_MAS.aut_n1
  is 'additional nvarchar2 aut_n1 to be used in future';
comment on column TB_LOCATION_MAS.aut_n2
  is 'additional nvarchar2 aut_n2 to be used in future';
comment on column TB_LOCATION_MAS.aut_n3
  is 'additional nvarchar2 aut_n3 to be used in future';
comment on column TB_LOCATION_MAS.aut_n4
  is 'additional nvarchar2 aut_n4 to be used in future';
comment on column TB_LOCATION_MAS.aut_n5
  is 'additional nvarchar2 aut_n5 to be used in future';
comment on column TB_LOCATION_MAS.aut_d1
  is 'additional nvarchar2 aut_d1 to be used in future';
comment on column TB_LOCATION_MAS.aut_d2
  is 'additional nvarchar2 aut_d2 to be used in future';
comment on column TB_LOCATION_MAS.aut_d3
  is 'additional nvarchar2 aut_d3 to be used in future';
comment on column TB_LOCATION_MAS.aut_d4
  is 'additional nvarchar2 aut_d4 to be used in future';
comment on column TB_LOCATION_MAS.aut_d5
  is 'additional nvarchar2 aut_d5 to be used in future';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_LOCATION_MAS
  add constraint PK_LOC_ID primary key (LOC_ID);
alter table TB_LOCATION_MAS
  add constraint FK_ORGID_LOC foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
