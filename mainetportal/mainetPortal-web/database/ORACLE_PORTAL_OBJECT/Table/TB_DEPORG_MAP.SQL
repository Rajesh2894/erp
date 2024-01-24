-- Create table
create table TB_DEPORG_MAP
(
  map_id        NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  map_status    CHAR(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  com_v1        NVARCHAR2(100),
  com_v2        NVARCHAR2(100),
  com_v3        NVARCHAR2(100),
  com_v4        NVARCHAR2(100),
  com_v5        NVARCHAR2(100),
  com_n1        NUMBER(15),
  com_n2        NUMBER(15),
  com_n3        NUMBER(15),
  com_n4        NUMBER(15),
  com_n5        NUMBER(15),
  com_d1        DATE,
  com_d2        DATE,
  com_d3        DATE,
  com_lo1       CHAR(1),
  com_lo2       CHAR(1),
  com_lo3       CHAR(1),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the table 
comment on table TB_DEPORG_MAP
  is 'COMMON TABLE USED TO CREATE PARENT CHILD RELATIONSHIP. THIS IS A CHILD TABLE';
-- Add comments to the columns 
comment on column TB_DEPORG_MAP.orgid
  is 'ORGANISATION ID';
comment on column TB_DEPORG_MAP.user_id
  is 'USER ID';
comment on column TB_DEPORG_MAP.lang_id
  is 'LANGUAGE ID';
comment on column TB_DEPORG_MAP.lmoddate
  is 'CREATION DATE';
comment on column TB_DEPORG_MAP.updated_by
  is 'UPDATED BY USER ID';
comment on column TB_DEPORG_MAP.updated_date
  is 'UPDATION DATE';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_DEPORG_MAP
  add constraint PK_MAPID primary key (MAP_ID);
alter table TB_DEPORG_MAP
  add constraint FK_DEPTID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
