delete  from employee where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from role_entitlement where org_id in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_aboutus where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_aboutus_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_announcement where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_announcement_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_contact_us where org_id in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_contact_us_hist where org_id in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_faq where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_faq_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_feedback_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_home_images where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_home_images_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_links_master where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_links_master_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_profile_master where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_profile_master_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_public_notices where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_public_notices_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_sub_link_field_map where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_sub_link_field_map_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_sub_link_fields_dtl where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_sub_link_fields_dtl_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_sub_links_master where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_eip_sub_links_master_hist where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_group_mast where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_page_master where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_portal_application_mst where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_portal_service_master where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_portal_sms_integration where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from tb_portal_sms_mail_template where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete  from thememaster where orgid in 
(select orgid from tb_organisation where ORG_STATUS='I');

delete from tb_organisation where ORG_STATUS='I';

delete from employee where EMPLOGINNAME not in ('MBA','MBA_CK','NOUSER');
commit;

delete from role_entitlement where role_id in
(select gm_id from tb_group_mast where gr_code in ('GR_AGENCY_DEFAULT','GR_CITIZEN_DEFAULT'));
commit;

delete from tb_cm_onl_tran_mas_portal;
commit;

drop table tb_custbanks;
delete from tb_portal_application_mst;
commit;

