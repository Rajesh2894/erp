-- Create table
create table TB_GROUP_MAST_HIST
(
  H_GMID        NUMBER not null,
  gm_id         NUMBER not null,
  gr_code       NVARCHAR2(20),
  gr_name       NVARCHAR2(500),
  gr_name_reg   NVARCHAR2(500),
  gr_desc_eng   NVARCHAR2(500),
  gr_desc_reg   NVARCHAR2(1000),
  orgid         NUMBER,
  org_specific  CHAR(1) default 'N',
  gr_status     CHAR(1),
  lang_id       NUMBER,
  user_id       NUMBER,
  entry_date    DATE,
  lg_ip_mac     NVARCHAR2(100),
  updated_by    NUMBER,
  updated_date  DATE,
  lg_ip_mac_upd NVARCHAR2(100),
  gr_default    CHAR(1),
  H_STATUS      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_GROUP_MAST_HIST
  add constraint PK_H_GMID primary key (H_GMID);
