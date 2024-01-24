-- Create table
create table TB_COMPARENT_MAS_HIST
(
  h_comid            NUMBER(12) not null,
  com_id             NUMBER(12) not null,
  cpm_id             NUMBER(12) not null,
  com_desc           NVARCHAR2(200) not null,
  com_value          NVARCHAR2(200) not null,
  com_level          NUMBER(3) not null,
  com_status         NVARCHAR2(1),
  orgid              NUMBER(4) not null,
  user_id            NUMBER(7) not null,
  lang_id            NUMBER(7) not null,
  lmoddate           DATE not null,
  updated_by         NUMBER(7),
  updated_date       DATE,
  com_desc_mar       NVARCHAR2(400),
  com_replicate_flag CHAR(1) default 'N',
  lg_ip_mac          VARCHAR2(100),
  lg_ip_mac_upd      VARCHAR2(100),
  h_status           NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COMPARENT_MAS_HIST
  add constraint PK_H_COMID primary key (H_COMID);
