alter table TB_AS_PRO_ASSESMENT_DETAIL drop column pro_assd_assesment_date;
alter table TB_AS_PRO_ASSESMENT_DETAIL add pro_assd_roadfactor number(12);
comment on column TB_AS_PRO_ASSESMENT_DETAIL.pro_assd_roadfactor
  is 'Road Factor';
