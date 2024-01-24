create table TB_COMPLAINT_TYPE
( type_id       SMALLINT not null,
  orgid         SMALLINT not null,
  description   NVARCHAR(1000) not null,
  status        VARCHAR(1) not null,
  updated_by    SMALLINT,
  updated_date  DATETIME,
  language_id   SMALLINT not null,
  create_date   DATETIME not null,
  created_by    SMALLINT,
  lg_ip_mac     NVARCHAR(300),
  lg_ip_mac_upd NVARCHAR(300));
create index COMP_TY_INDX on TB_COMPLAINT_TYPE (STATUS) ;
create index IX_TB_COM_TYPE on TB_COMPLAINT_TYPE (DESCRIPTION);
alter table TB_COMPLAINT_TYPE add constraint PK_TB_COM_TYPE primary key (TYPE_ID);