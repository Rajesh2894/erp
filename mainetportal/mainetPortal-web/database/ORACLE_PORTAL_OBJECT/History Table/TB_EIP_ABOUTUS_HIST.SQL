-- Create table
create table TB_EIP_ABOUTUS_HIST
(
  H_ABOUTUSID      NUMBER(12) not null,
  aboutus_id       NUMBER(12) not null,
  description_en   VARCHAR2(3000) not null,
  description_reg  NVARCHAR2(2000),
  isdeleted        VARCHAR2(1) default 'N' not null,
  orgid            NUMBER(4) not null,
  user_id          NUMBER(7) not null,
  lang_id          NUMBER(7) not null,
  created_date     DATE not null,
  updated_by       NUMBER(7),
  updated_date     DATE,
  lg_ip_mac        VARCHAR2(100),
  lg_ip_mac_upd    VARCHAR2(100),
  description_en1  VARCHAR2(3000) not null,
  description_reg1 NVARCHAR2(2000),
  H_STATUS         NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_ABOUTUS_HIST
  add constraint PK_H_ABOUTUSID primary key (H_ABOUTUSID);
