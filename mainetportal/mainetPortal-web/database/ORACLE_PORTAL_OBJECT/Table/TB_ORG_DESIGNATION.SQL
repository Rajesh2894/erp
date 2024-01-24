-- Create table
create table TB_ORG_DESIGNATION
(
  map_id        NUMBER(12) not null,
  dsgid         NUMBER(12) not null,
  map_status    CHAR(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  lmoddate      DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
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
  com_lo3       CHAR(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_ORG_DESIGNATION
  add constraint PK_ORG_DESIGNATION_MAPID primary key (MAP_ID);
alter table TB_ORG_DESIGNATION
  add constraint FK_DSGID foreign key (DSGID)
  references DESIGNATION (DSGID);
alter table TB_ORG_DESIGNATION
  add constraint FK_TB_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
