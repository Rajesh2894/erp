alter table TB_AS_ASSESMENT_FACTOR_DETAIL add assf_start_date date;
alter table TB_AS_ASSESMENT_FACTOR_DETAIL add assf_end_date date;
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_start_date
  is 'Factor Start Date';
comment on column TB_AS_ASSESMENT_FACTOR_DETAIL.assf_end_date
  is 'Factor End Date';