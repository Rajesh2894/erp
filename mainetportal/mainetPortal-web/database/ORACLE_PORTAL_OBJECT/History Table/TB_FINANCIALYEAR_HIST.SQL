-- Create table
create table TB_FINANCIALYEAR_HIST
(
  H_FAYEARID     NUMBER(12) not null,
  fa_yearid      NUMBER(12) not null,
  fa_fromdate    DATE,
  fa_todate      DATE,
  fa_closed      NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  lmoddate       DATE not null,
  fa_provclosing NVARCHAR2(1) default 'n' not null,
  updated_by     NUMBER(7),
  updated_date   DATE,
  H_STATUS       NVARCHAR2(1)
);
ALTER TABLE TB_FINANCIALYEAR_HIST
 ADD CONSTRAINTS PK_H_FAYEARID PRIMARY KEY (H_FAYEARID);
