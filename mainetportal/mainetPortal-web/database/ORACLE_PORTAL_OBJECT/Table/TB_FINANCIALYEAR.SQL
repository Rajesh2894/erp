-- Create table
create table TB_FINANCIALYEAR
(
  fa_yearid      NUMBER(12) not null,
  fa_fromdate    DATE,
  fa_todate      DATE,
  fa_closed      NVARCHAR2(1),
  user_id        NUMBER(7) not null,
  lang_id        NUMBER(7) not null,
  lmoddate       DATE not null,
  fa_provclosing NVARCHAR2(1) default 'n' not null,
  updated_by     NUMBER(7),
  updated_date   DATE
);
-- Add comments to the columns 
comment on column TB_FINANCIALYEAR.fa_yearid
  is 'Primary Key';
comment on column TB_FINANCIALYEAR.fa_fromdate
  is 'Start date of the financial year';
comment on column TB_FINANCIALYEAR.fa_todate
  is 'End date of the financial year';
comment on column TB_FINANCIALYEAR.fa_closed
  is 'Flag for Permanent Closing';
comment on column TB_FINANCIALYEAR.user_id
  is 'User Identity';
comment on column TB_FINANCIALYEAR.lang_id
  is 'Language Identity';
comment on column TB_FINANCIALYEAR.lmoddate
  is 'Last Modification Date';
comment on column TB_FINANCIALYEAR.fa_provclosing
  is 'Flag for Provisional Closing';
comment on column TB_FINANCIALYEAR.updated_by
  is 'Updated by';
comment on column TB_FINANCIALYEAR.updated_date
  is 'Updated Date';
-- Create/Recreate indexes 
create index IX_FRM_TO_DATE on TB_FINANCIALYEAR (FA_FROMDATE, FA_TODATE);
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_FINANCIALYEAR
  add constraint PK_FINANCIALYEAR_YEARID primary key (FA_YEARID);
