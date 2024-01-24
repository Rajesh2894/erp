alter table TB_AS_PRO_ASSESMENT_MAST add apm_application_id NUMBER(16);
comment on column TB_AS_PRO_ASSESMENT_MAST.apm_application_id
  is 'Application ID';
alter table TB_AS_PRO_ASSESMENT_OWNER_DTL modify pro_asso_addharno default null;
