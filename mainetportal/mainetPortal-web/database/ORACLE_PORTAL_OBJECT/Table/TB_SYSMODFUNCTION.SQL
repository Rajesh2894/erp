-- Create table
create table TB_SYSMODFUNCTION
(
  smfid          NUMBER(12) not null,
  smfname        NVARCHAR2(1000) not null,
  smfdescription NVARCHAR2(1000),
  smfflag        NVARCHAR2(1),
  smfaction      NVARCHAR2(200),
  smfcategory    NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  ondate         DATE not null,
  ontime         VARCHAR2(12) not null,
  action         VARCHAR2(1),
  isdeleted      VARCHAR2(1),
  smfsystemid    NUMBER(12),
  smfcode        NVARCHAR2(255),
  updated_by     NUMBER(7),
  updated_date   DATE,
  lang_id        NUMBER(7),
  smfname_mar    NVARCHAR2(1000),
  smfsrno        NUMBER(3),
  lg_ip_mac      VARCHAR2(100),
  lg_ip_mac_upd  VARCHAR2(100),
  sm_parent_id   NUMBER(12),
  depth          NUMBER(1),
  sm_param1      NVARCHAR2(150),
  sm_param2      NVARCHAR2(150)
);
-- Add comments to the columns 
comment on column TB_SYSMODFUNCTION.sm_param1
  is 'Parameter value 1 if any';
comment on column TB_SYSMODFUNCTION.sm_param2
  is 'Parameter value 2 if any';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_SYSMODFUNCTION
  add constraint PK_SMFID primary key (SMFID);
