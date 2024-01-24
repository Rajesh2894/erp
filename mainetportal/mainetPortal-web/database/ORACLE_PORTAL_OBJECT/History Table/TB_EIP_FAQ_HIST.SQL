-- Create table
create table TB_EIP_FAQ_HIST
(
  H_FAQID       NUMBER(12) not null,
  faq_id        NUMBER(12) not null,
  answer_en     VARCHAR2(500) not null,
  answer_reg    NVARCHAR2(1000),
  question_en   VARCHAR2(250) not null,
  question_reg  NVARCHAR2(1000),
  seq_no        NUMBER(19),
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  H_STATUS      NVARCHAR2(1)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_FAQ_HIST
  add constraint PK_H_FAQID primary key (H_FAQID);
