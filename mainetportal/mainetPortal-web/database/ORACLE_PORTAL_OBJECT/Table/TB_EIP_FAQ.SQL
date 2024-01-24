-- Create table
create table TB_EIP_FAQ
(
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
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_EIP_FAQ.faq_id
  is 'Primary Key';
comment on column TB_EIP_FAQ.answer_en
  is 'Answer in english language';
comment on column TB_EIP_FAQ.answer_reg
  is 'Answer in regional language';
comment on column TB_EIP_FAQ.question_en
  is 'Question in english language';
comment on column TB_EIP_FAQ.question_reg
  is 'Question in regional language';
comment on column TB_EIP_FAQ.seq_no
  is 'Sequence';
comment on column TB_EIP_FAQ.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_EIP_FAQ.orgid
  is 'Organization Id';
comment on column TB_EIP_FAQ.user_id
  is 'User Id';
comment on column TB_EIP_FAQ.lang_id
  is 'Language Id';
comment on column TB_EIP_FAQ.created_date
  is 'Created Date';
comment on column TB_EIP_FAQ.updated_by
  is 'Modified By';
comment on column TB_EIP_FAQ.updated_date
  is 'Modification Date';
comment on column TB_EIP_FAQ.lg_ip_mac
  is 'Client Machine''''''''s Login Name | IP Address | Physical Address';
comment on column TB_EIP_FAQ.lg_ip_mac_upd
  is 'Updated Client Machine''''''''s Login Name | IP Address | Physical Address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_EIP_FAQ
  add constraint PK_EIP_FAQ_ID primary key (FAQ_ID);
alter table TB_EIP_FAQ
  add constraint FK_EIP_FAQ_ORG_ID foreign key (ORGID)
  references TB_ORGANISATION (ORGID);
alter table TB_EIP_FAQ
  add constraint FK_FAQ_EMPID foreign key (USER_ID)
  references EMPLOYEE (EMPID);
alter table TB_EIP_FAQ
  add constraint FK_FAQ_UPD_EMPID foreign key (UPDATED_BY)
  references EMPLOYEE (EMPID);
