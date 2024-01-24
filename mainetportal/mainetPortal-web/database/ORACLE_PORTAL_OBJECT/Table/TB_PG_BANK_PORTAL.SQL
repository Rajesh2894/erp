-- Create table
create table TB_PG_BANK_PORTAL
(
  pbp_id         NUMBER(12) not null,
  pbp_name       NVARCHAR2(500) not null,
  pbp_short_desc NVARCHAR2(50),
  pbp_bank_code  NUMBER(20) not null,
  cb_bankid      NUMBER(12),
  orgid          NUMBER(4),
  user_id        NUMBER(7),
  lang_id        NUMBER(7),
  lmoddate       DATE,
  isdeleted      NUMBER(1),
  pbp_name_mar   NVARCHAR2(500)
);
