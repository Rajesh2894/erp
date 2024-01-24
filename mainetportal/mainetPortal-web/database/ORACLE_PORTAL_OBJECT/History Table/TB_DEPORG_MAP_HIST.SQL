-- Create table
create table TB_DEPORG_MAP_HIST
(
  h_mapid       NUMBER(12) not null,
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
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
);
ALTER table TB_DEPORG_MAP_HIST
  add constraint PK_h_mapid primary key (h_mapid);
