--liquibase formatted sql
--changeset nilima:V20170412115619__CR_TB_CONTRACT_MAST_DDL.sql
create table TB_CONTRACT_MAST
( cont_id               NUMBER(12) not null,
  cont_dept             NUMBER(12) not null,
  cont_no               NVARCHAR2(20) not null,
  cont_date             DATE not null,
  cont_tnd_no           NVARCHAR2(20) not null,
  cont_tnd_date         DATE not null,
  cont_rso_no           NVARCHAR2(20) not null,
  cont_rso_date         DATE not null,
  cont_type             NUMBER(12) not null,
  cont_mode             CHAR(1),
  cont_pay_type         CHAR(1),
  cont_renewal          CHAR(1) not null,
  cont_active           CHAR(1) not null,
  cont_close_flag       CHAR(1) not null,
  cont_aut_status       CHAR(1),
  cont_aut_by           NUMBER(12),
  cont_aut_date         DATE,
  cont_terminat_by      CHAR(1),
  cont_termination_date DATE,
  orgid                 NUMBER(12) not null,
  created_by            NUMBER(12) not null,
  lang_id               NUMBER(7) not null,
  created_date          DATE not null,
  updated_by            NUMBER(12),
  updated_date          DATE,
  lg_ip_mac             VARCHAR2(100) not null,
  lg_ip_mac_upd         VARCHAR2(100),
  cont_map_flag         CHAR(1)
);
comment on table TB_CONTRACT_MAST
  is 'Table for Contract Creation,Contract renewal,contract revision';
comment on column TB_CONTRACT_MAST.cont_id
  is 'primary key';
comment on column TB_CONTRACT_MAST.cont_dept
  is 'Contract Department(department selected in Party1 details)';
comment on column TB_CONTRACT_MAST.cont_no
  is 'Contract No. (ORGID+DEPTCODE+FINANCIAL YEAR+999)';
comment on column TB_CONTRACT_MAST.cont_date
  is 'Contract Date/Renewal Date/Revision Date';
comment on column TB_CONTRACT_MAST.cont_tnd_no
  is 'Tender No.';
comment on column TB_CONTRACT_MAST.cont_tnd_date
  is 'Tender Date';
comment on column TB_CONTRACT_MAST.cont_rso_no
  is 'Resolution No.';
comment on column TB_CONTRACT_MAST.cont_rso_date
  is 'Resolution Date';
comment on column TB_CONTRACT_MAST.cont_type
  is 'Contract Type value from Non Hirarchey Prefix(CNT)';
comment on column TB_CONTRACT_MAST.cont_mode
  is 'Contract Mode(Commercial ''C'',Non Commercial ''N'')';
comment on column TB_CONTRACT_MAST.cont_pay_type
  is 'Contract Payment Type (Payable ''P'',Receivable ''R'')';
comment on column TB_CONTRACT_MAST.cont_renewal
  is 'Renewal of Contract (''Y'',''N)';
comment on column TB_CONTRACT_MAST.cont_active
  is 'flag to identify whether the record is deleted or not. ''y''  for not deleted (active) record and ''n'' for deleted (inactive) .';
comment on column TB_CONTRACT_MAST.cont_close_flag
  is 'Contract Close Flag (N,Y)';
comment on column TB_CONTRACT_MAST.cont_aut_status
  is 'authorisation status';
comment on column TB_CONTRACT_MAST.cont_aut_by
  is 'authorisation by (empid)';
comment on column TB_CONTRACT_MAST.cont_aut_date
  is 'authorisation date';
comment on column TB_CONTRACT_MAST.cont_terminat_by
  is 'Contract Terminated By';
comment on column TB_CONTRACT_MAST.cont_termination_date
  is 'Contract Termination Date';
comment on column TB_CONTRACT_MAST.orgid
  is 'orgnisation id';
comment on column TB_CONTRACT_MAST.created_by
  is 'user id who created the record';
comment on column TB_CONTRACT_MAST.lang_id
  is 'language identity';
comment on column TB_CONTRACT_MAST.created_date
  is 'record creation date';
comment on column TB_CONTRACT_MAST.updated_by
  is 'user id who update the data';
comment on column TB_CONTRACT_MAST.updated_date
  is 'date on which data is updated';
comment on column TB_CONTRACT_MAST.lg_ip_mac
  is 'client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_MAST.lg_ip_mac_upd
  is 'updated client machine?s login name | ip address | physical address';
comment on column TB_CONTRACT_MAST.cont_map_flag
  is 'Contract Map Flag (N,Y)';
create index INDX_CONT_NO on TB_CONTRACT_MAST (CONT_NO, ORGID) ;
alter table TB_CONTRACT_MAST
  add constraint PK_CONT_ID primary key (CONT_ID);
