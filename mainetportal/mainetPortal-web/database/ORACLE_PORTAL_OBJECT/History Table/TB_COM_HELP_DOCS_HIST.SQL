-- Create table
create table TB_COM_HELP_DOCS_HIST
(
  H_DOC_ID      NUMBER(12) not null,
  help_doc_id   NUMBER(12) not null,
  module_name   VARCHAR2(200) not null,
  file_name_eng VARCHAR2(100) not null,
  file_path_eng VARCHAR2(2000),
  file_type_eng VARCHAR2(1) not null,
  file_name_reg VARCHAR2(100),
  file_path_reg VARCHAR2(2000),
  file_type_reg VARCHAR2(1),
  deptname      VARCHAR2(50),
  attach_by     NUMBER(12),
  attach_on     DATE,
  isdeleted     VARCHAR2(1) default 'N' not null,
  orgid         NUMBER(4) not null,
  user_id       NUMBER(7) not null,
  lang_id       NUMBER(7) not null,
  created_date  DATE not null,
  updated_by    NUMBER(7),
  updated_date  DATE,
  lg_ip_mac     VARCHAR2(100),
  lg_ip_mac_upd VARCHAR2(100),
  H_STATUS      VARCHAR2(1)
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COM_HELP_DOCS_HIST
  add constraint PK_H_DOC_ID primary key (H_DOC_ID);
