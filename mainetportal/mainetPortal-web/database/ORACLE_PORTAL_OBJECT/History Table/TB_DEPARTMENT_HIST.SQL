-- Create table
create table TB_DEPARTMENT_HIST
(
  h_deptid      NUMBER(12) not null,
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
  lg_ip_mac_upd VARCHAR2(100),
  h_status      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_DEPARTMENT_HIST
  add constraint PK_H_DEPTID primary key (H_DEPTID);
