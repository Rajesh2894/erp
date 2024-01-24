-- Create table
create table TB_DEPARTMENT
(
  dp_deptid     NUMBER(12) not null,
  dp_deptcode   NVARCHAR2(4),
  dp_deptdesc   NVARCHAR2(400),
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(4) not null,
  lmoddate      DATE not null,
  status        NVARCHAR2(1),
  dp_shortdesc  NVARCHAR2(10),
  dp_name_mar   NVARCHAR2(2000),
  sub_dept_flg  CHAR(1),
  updated_by    NUMBER(7),
  updated_date  DATE,
  dp_smfid      NUMBER(12),
  dp_check      NVARCHAR2(1),
  dp_prefix     NVARCHAR2(3),
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_DEPARTMENT.dp_deptid
  is 'Primary Key';
comment on column TB_DEPARTMENT.dp_deptcode
  is 'Code of the Department';
comment on column TB_DEPARTMENT.dp_deptdesc
  is 'Department Name';
comment on column TB_DEPARTMENT.user_id
  is 'User Identity';
comment on column TB_DEPARTMENT.lang_id
  is 'Language Identity';
comment on column TB_DEPARTMENT.lmoddate
  is 'Last Modification Date';
comment on column TB_DEPARTMENT.status
  is 'Status Active/Inactive';
comment on column TB_DEPARTMENT.dp_shortdesc
  is 'Short Description';
comment on column TB_DEPARTMENT.dp_name_mar
  is 'Department Marathi Name';
comment on column TB_DEPARTMENT.lg_ip_mac
  is 'Client Machineâ€™s Login Name | IP Address | Physical Address';
comment on column TB_DEPARTMENT.lg_ip_mac_upd
  is 'Updated Client Machineâ€™s Login Name | IP Address | Physical Address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_DEPARTMENT
  add constraint PK_DEPARTMENT_DP_DEPTID primary key (DP_DEPTID);
alter table TB_DEPARTMENT
  add constraint UK_DP_DEPTCODE unique (DP_DEPTCODE);
