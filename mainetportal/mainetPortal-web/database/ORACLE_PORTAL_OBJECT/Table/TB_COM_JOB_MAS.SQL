-- Create table
create table TB_COM_JOB_MAS
(
  cj_id        NUMBER(12) not null,
  dp_deptid    NUMBER(12) not null,
  cj_no        VARCHAR2(100),
  cj_desc      VARCHAR2(500),
  cj_nxdt      DATE,
  cj_interval  VARCHAR2(500),
  status       VARCHAR2(1),
  orgid        NUMBER(5),
  user_id      NUMBER(7) not null,
  lang_id      NUMBER(4) not null,
  lmoddate     DATE not null,
  updated_by   NUMBER(7),
  updated_date DATE,
  com_v1       NVARCHAR2(100),
  com_v2       NVARCHAR2(100),
  com_v3       NVARCHAR2(100),
  com_v4       NVARCHAR2(100),
  com_v5       NVARCHAR2(100),
  com_n1       NUMBER(15),
  com_n2       NUMBER(15),
  com_n3       NUMBER(15),
  com_n4       NUMBER(15),
  com_n5       NUMBER(15),
  com_d1       DATE,
  com_d2       DATE,
  com_d3       DATE,
  com_lo1      CHAR(1),
  com_lo2      CHAR(1),
  com_lo3      CHAR(1),
  cpd_id_bjo   NUMBER(12),
  cpd_id_bfr   NUMBER(12),
  cj_repeat    VARCHAR2(1),
  cj_classname VARCHAR2(1000),
  cj_funname   VARCHAR2(100),
  cj_date      DATE,
  created_date DATE
);
-- Add comments to the columns 
comment on column TB_COM_JOB_MAS.cj_id
  is 'Primary Key';
comment on column TB_COM_JOB_MAS.dp_deptid
  is 'department';
comment on column TB_COM_JOB_MAS.cj_no
  is 'job number';
comment on column TB_COM_JOB_MAS.cj_desc
  is 'Procedure and function to be excuted.';
comment on column TB_COM_JOB_MAS.cj_nxdt
  is 'Next date';
comment on column TB_COM_JOB_MAS.cj_interval
  is 'interval';
comment on column TB_COM_JOB_MAS.status
  is 'status';
comment on column TB_COM_JOB_MAS.user_id
  is 'User Identity';
comment on column TB_COM_JOB_MAS.lang_id
  is 'Language Identity';
comment on column TB_COM_JOB_MAS.lmoddate
  is 'Last Modification Date';
comment on column TB_COM_JOB_MAS.updated_by
  is 'Modification By';
comment on column TB_COM_JOB_MAS.updated_date
  is 'Modification Date';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COM_JOB_MAS
  add constraint PK_CJ_ID primary key (CJ_ID);
alter table TB_COM_JOB_MAS
  add constraint FK_COM_DEPT_ID foreign key (DP_DEPTID)
  references TB_DEPARTMENT (DP_DEPTID);
