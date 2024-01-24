-- Create table
create table TB_COM_HELP_DOCS
(
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
  lg_ip_mac_upd VARCHAR2(100)
);
-- Add comments to the columns 
comment on column TB_COM_HELP_DOCS.help_doc_id
  is 'Primary Key';
comment on column TB_COM_HELP_DOCS.module_name
  is 'Name of the module for which the help doc is being uploaded';
comment on column TB_COM_HELP_DOCS.file_name_eng
  is 'Name of the file being uploaded';
comment on column TB_COM_HELP_DOCS.file_path_eng
  is 'Path of the file being uploaded';
comment on column TB_COM_HELP_DOCS.file_type_eng
  is 'Type of the file being uploaded';
comment on column TB_COM_HELP_DOCS.file_name_reg
  is 'Name of the file being uploaded for regional';
comment on column TB_COM_HELP_DOCS.file_path_reg
  is 'Path of the file being uploaded for regional';
comment on column TB_COM_HELP_DOCS.file_type_reg
  is 'Type of the file being uploaded for regional';
comment on column TB_COM_HELP_DOCS.deptname
  is 'dept name';
comment on column TB_COM_HELP_DOCS.attach_by
  is 'Person by whom the file has been uploaded';
comment on column TB_COM_HELP_DOCS.attach_on
  is 'Date on which the file has been uploaded';
comment on column TB_COM_HELP_DOCS.isdeleted
  is 'Record Deletion flag - value N non-deleted record and Y- deleted record';
comment on column TB_COM_HELP_DOCS.orgid
  is 'Organization Id.';
comment on column TB_COM_HELP_DOCS.user_id
  is 'User Id';
comment on column TB_COM_HELP_DOCS.lang_id
  is 'Language Id';
comment on column TB_COM_HELP_DOCS.created_date
  is 'Last Modification Date';
comment on column TB_COM_HELP_DOCS.updated_by
  is 'Modified By';
comment on column TB_COM_HELP_DOCS.updated_date
  is 'Modification Date';
comment on column TB_COM_HELP_DOCS.lg_ip_mac
  is 'Client Machine''''s Login Name | IP Address | Physical Address';
comment on column TB_COM_HELP_DOCS.lg_ip_mac_upd
  is 'Updated Client Machine''''s Login Name | IP Address | Physical Address';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_COM_HELP_DOCS
  add constraint PK_HELP_DOC_ID primary key (HELP_DOC_ID);
