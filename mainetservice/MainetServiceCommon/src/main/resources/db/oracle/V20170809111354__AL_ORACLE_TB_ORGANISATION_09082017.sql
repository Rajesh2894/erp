alter table TB_ORGANISATION modify o_nls_orgname not null;
alter table TB_ORGANISATION add org_gst_no varchar2(15);
comment on column TB_ORGANISATION.ulb_org_id
  is 'Ulb Organisation Id';
comment on column TB_ORGANISATION.org_cpd_id_state
  is 'State from prefix ''STT''';
comment on column TB_ORGANISATION.org_gst_no
  is 'GST NO';