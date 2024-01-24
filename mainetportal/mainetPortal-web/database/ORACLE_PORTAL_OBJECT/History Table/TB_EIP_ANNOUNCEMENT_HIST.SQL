-- Create table
create table TB_EIP_ANNOUNCEMENT_HIST
(
  H_ANNOUNCEID      NUMBER(12) not null,
  announce_id       NUMBER(12) not null,
  announce_desc_eng NVARCHAR2(2000) not null,
  announce_desc_reg NVARCHAR2(2000) not null,
  attach            NVARCHAR2(100),
  isdeleted         VARCHAR2(1) default 'N' not null,
  orgid             NUMBER(4) not null,
  user_id           NUMBER(7) not null,
  lang_id           NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100),
  H_STATUS          NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_ANNOUNCEMENT_HIST
  add constraint PK_H_ANNOUNCEID primary key (H_ANNOUNCEID);
