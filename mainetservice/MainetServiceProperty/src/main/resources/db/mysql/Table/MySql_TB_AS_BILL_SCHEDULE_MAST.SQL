create table TB_AS_BILL_SCHEDULE_MAST
( as_bill_scheid    NUMBER(12) not null,
  fa_yearid         NUMBER(12) not null,
  as_bill_frequency NUMBER(12) not null,
  as_frequency_from NUMBER(12) not null,
  as_frequency_to   NUMBER(12) not null,
  as_aut_status     CHAR(1) not null,
  as_aut_by         CHAR(1) not null,
  as_aut_date       DATE not null,
  orgid             NUMBER(12) not null,
  created_by        NUMBER(7) not null,
  created_date      DATE not null,
  updated_by        NUMBER(7),
  updated_date      DATE,
  lg_ip_mac         VARCHAR2(100),
  lg_ip_mac_upd     VARCHAR2(100));

 alter table TB_AS_BILL_SCHEDULE_MAST
  add constraint PK_AS_BILL_SCHEID primary key (AS_BILL_SCHEID);
alter table TB_AS_BILL_SCHEDULE_MAST
  add constraint FK_BILLSCHE_FA_YEARID foreign key (FA_YEARID)
  references TB_FINANCIALYEAR (FA_YEARID);

  
comment 'Primary Key';
comment 'Foregin Key (tb_financialyear)';
comment 'Billing Frequency(Prefix ''BSC'')';
comment 'Billing From Month';
comment 'Billing To Month';
comment 'authorisation status';
comment 'authorisation by (empid)';
comment 'authorisation date';
comment 'orgnisation id';
comment 'user id who created the record';
comment 'record creation date';
comment 'user id who update the data';
comment 'date on which data is going to update';
comment 'client machine?s login name | ip address | physical address';
comment 'updated client machine?s login name | ip address | physical address';
