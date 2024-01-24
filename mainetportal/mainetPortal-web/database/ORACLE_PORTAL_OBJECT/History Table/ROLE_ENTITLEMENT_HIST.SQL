-- Create table
create table ROLE_ENTITLEMENT_HIST
(
  H_role_et_ID NUMBER not null,
  role_et_id   NUMBER not null,
  role_id      NUMBER,
  smfid        NUMBER,
  updated_by   NUMBER,
  updated_date DATE,
  is_active    NVARCHAR2(1),
  et_parent_id NUMBER,
  org_id       NUMBER,
  parent_id    NUMBER,
  dp_deptid    NUMBER,
  H_STATUS     NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table ROLE_ENTITLEMENT_HIST
  add constraint PK_H_ROLE_ET_ID primary key (H_role_et_ID);
