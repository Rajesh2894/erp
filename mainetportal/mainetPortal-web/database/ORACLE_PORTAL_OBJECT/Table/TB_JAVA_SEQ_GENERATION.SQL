-- Create table
create table TB_JAVA_SEQ_GENERATION
(
  sq_id         NUMBER(12) not null,
  sq_mdl_name   NVARCHAR2(20),
  sq_tbl_name   NVARCHAR2(50),
  sq_fld_name   NVARCHAR2(50),
  sq_seq_name   NVARCHAR2(100),
  sq_str_with   NUMBER(12),
  sq_max_num    NUMBER(12),
  sq_str_date   DATE,
  sq_nxt_rst_dt DATE,
  sq_lst_rst_dt DATE,
  sq_rst_typ    NVARCHAR2(1),
  sq_ctr_id     NVARCHAR2(30)
);
-- Add comments to the table 
comment on table TB_JAVA_SEQ_GENERATION
  is 'Table for Automatic Sequence Creation, Generation and Reset';
-- Add comments to the columns 
comment on column TB_JAVA_SEQ_GENERATION.sq_id
  is 'Sequnce Id(PK)';
comment on column TB_JAVA_SEQ_GENERATION.sq_mdl_name
  is 'Module Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_tbl_name
  is 'Table Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_fld_name
  is 'Field Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_seq_name
  is 'Sequence Name';
comment on column TB_JAVA_SEQ_GENERATION.sq_str_with
  is 'Start With';
comment on column TB_JAVA_SEQ_GENERATION.sq_max_num
  is 'Maximum Number';
comment on column TB_JAVA_SEQ_GENERATION.sq_str_date
  is 'Sequnce firstly start date';
comment on column TB_JAVA_SEQ_GENERATION.sq_nxt_rst_dt
  is 'Sequence Next Reset Date';
comment on column TB_JAVA_SEQ_GENERATION.sq_lst_rst_dt
  is 'Sequnce Last Reset Date';
comment on column TB_JAVA_SEQ_GENERATION.sq_rst_typ
  is 'Reset Type(F/Y/M/D/C)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TB_JAVA_SEQ_GENERATION
  add constraint PK_JAVA_SQ_GEN_SQID primary key (SQ_ID);
