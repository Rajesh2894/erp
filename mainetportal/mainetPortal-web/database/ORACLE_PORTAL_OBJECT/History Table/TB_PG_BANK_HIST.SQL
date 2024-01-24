-- Create table
create table TB_PG_BANK_HIST
(
  H_BMID        NUMBER(12),
  bm_bankid     NUMBER(12),
  pb_merchantid VARCHAR2(40),
  user_id       NUMBER(7),
  lang_id       NUMBER(7),
  lmoddate      DATE default (sysdate),
  orgid         NUMBER(4),
  bm_active     VARCHAR2(1) default 'A' not NULL,
  H_STATUS      VARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_PG_BANK_HIST
  add constraint PK_H_BMID primary key (H_BMID);
 
