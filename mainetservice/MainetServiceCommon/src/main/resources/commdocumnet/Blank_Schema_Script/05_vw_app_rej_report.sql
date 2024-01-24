create or replace force view vw_app_rej_report as
select distinct /*rm.cfc_rejection_id,*/ am.apm_application_id,
                to_char (am.apm_application_date, 'DD/MM/RRRR') adate,
                 sm.sm_service_name  service,
               fn_getcpddesc (am.apm_title, 'E',am.orgid)
                || ' '
                || am.apm_fname
                || ' '
                || am.apm_mname
                || ' '
                || am.apm_lname aname,
               /*fn_getcpddesc(ad.apa_blockno,'E',ad.orgid)
                || ' '
                || ad.apa_blockno
                || ' '
                || ad.apa_floor
                || ' '
                || ad.apa_wing
                || ' '
                || ad.apa_bldgnm
                || ' '
                || ad.apa_hsg_cmplxnm
                || ' '
                || ad.apa_roadnm
                || ' '
                || ad.apa_areanm
                || ' '
                || ad.apa_pincode aaddress,*/  --commented by ashish mahadik against D-7651 on 29-01-2016
               am.orgid/*,
               am.rejction_no,
               am.rejection_dt*/
           from tb_cfc_application_mst am,
                tb_services_mst sm,
                /*tb_cfc_rejection rm,*/
                tb_cfc_application_address ad
          where am.sm_service_id = sm.sm_service_id
            /*and am.apm_application_id = rm.apm_application_id*/  --commented by ashish mahadik against D-7651 on 29-01-2016
            and am.apm_application_id = ad.apm_application_id
            and am.orgid = sm.orgid
            and sm.orgid = ad.orgid
          order by am.apm_application_id;
