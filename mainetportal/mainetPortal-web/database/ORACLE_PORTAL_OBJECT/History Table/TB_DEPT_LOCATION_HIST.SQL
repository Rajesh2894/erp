-- Create table
create table TB_DEPT_LOCATION_HIST
(
  H_MAPID       NUMBER(12) not null,
  mapid         NUMBER(12) not null,
  dp_deptid     NUMBER(12) not null,
  loc_id        NUMBER(12),
  isdeleted     VARCHAR2(1),
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4),
  isregistry    NUMBER,
  isrecordroom  NUMBER,
  isoutward     NUMBER,
  isnetwork     NUMBER,
  dskid         NUMBER(12),
  ondate        DATE,
  ontime        VARCHAR2(12),
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  aut_v1        NVARCHAR2(100),
  aut_v2        NVARCHAR2(100),
  aut_v3        NVARCHAR2(100),
  aut_v4        NVARCHAR2(100),
  aut_v5        NVARCHAR2(100),
  aut_n1        NUMBER(15),
  aut_n2        NUMBER(15),
  aut_n3        NUMBER(15),
  aut_n4        NUMBER(15),
  aut_n5        NUMBER(15),
  aut_d1        DATE,
  aut_d2        DATE,
  aut_d3        DATE,
  aut_lo1       CHAR(1),
  aut_lo2       CHAR(1),
  aut_lo3       CHAR(1),
  H_STATUS      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_DEPT_LOCATION_HIST
  add constraint PK_HMAPID primary key (H_MAPID);
 
