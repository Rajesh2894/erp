--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES.sql
DROP TABLE if exists tb_eip_tenders_mas; 
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES1.sql
DROP TABLE if exists tb_eip_tenders_detail;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES2.sql
DROP TABLE if exists tb_location_mas_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES3.sql
DROP TABLE if exists tb_location_admin_wardzone;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES4.sql
DROP TABLE if exists tb_location_elect_wardzone;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES5.sql
DROP TABLE if exists tb_location_oper_wardzone;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES6.sql
DROP TABLE if exists tb_location_revenue_wardzone; 
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES7.sql
DROP TABLE if exists tb_location_mas;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES8.sql
DROP TABLE if exists tb_workflow_request;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES9.sql
DROP TABLE if exists tb_workflow_employee;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES10.sql
DROP TABLE if exists tb_workflow_description;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES11.sql
DROP TABLE if exists  tb_workflow_actor;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES12.sql
DROP TABLE if exists  tb_workflow_action_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES13.sql
DROP TABLE if exists tb_workflow_action;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES14.sql
DROP TABLE if exists tb_portal_service_doc;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES15.sql
DROP TABLE if exists tb_pg_transaction_portal_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES16.sql
DROP TABLE if exists tb_pg_transaction_portal;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES17.sql
DROP TABLE if exists tb_pg_bank_portal_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES18.sql
DROP TABLE if exists tb_pg_bank_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES19.sql
DROP TABLE if exists  tb_org_designation;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES20.sql
DROP TABLE if exists  tb_financialyear_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES21.sql
DROP TABLE if exists tb_financialyear;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES22.sql
DROP TABLE if exists tb_eip_sub_link_fie_map_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES23.sql
DROP TABLE if exists tb_eip_sub_link_fie_dtl_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES24.sql
DROP TABLE if exists  tb_eip_forms_master;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES25.sql
DROP TABLE if exists tb_eip_announc_landing_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES26.sql
DROP TABLE if exists tb_dept_location_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES27.sql
DROP TABLE if exists tb_dept_location;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES28.sql
DROP TABLE if exists tb_dept_complaint_type;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES29.sql
DROP TABLE if exists tb_complaint_type_care;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES30.sql
DROP TABLE if exists tb_care_request;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES31.sql
DROP TABLE if exists tb_care_reopen_details;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES32.sql
DROP TABLE if exists tb_care_feedback;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES33.sql
DROP TABLE if exists tb_care_event;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES34.sql
DROP TABLE if exists tb_care_details;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES35.sql
DROP TABLE if exists tb_care_department_ref_details;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES36.sql
DROP TABLE if exists tb_care_department_action;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES37.sql
DROP TABLE if exists tb_care_applicant_details;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES38.sql
DROP TABLE if exists tb_bankmaster_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES39.sql
DROP TABLE if exists tb_bankmaster;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES40.sql
DROP TABLE if exists tb_bankaccount_hist;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES41.sql
DROP TABLE if exists tb_bankaccount;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES42.sql
DROP TABLE if exists tb_attach_cfc_draft;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES43.sql
DROP TABLE if exists task_request_view;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES44.sql
DROP TABLE if exists care_location_details_seq;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES45.sql
DROP TABLE if exists care_location_details;
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES46.sql
DROP TABLE if exists tb_portal_pfix_mapping_master
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES47.sql
DROP TABLE if exists tb_services_mst
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES48.sql
DROP TABLE if exists tb_employee
--liquibase formatted sql
--changeset priya:V20180208111513__DR_PORTAL_TABLES49.sql
DROP TABLE if exists tb_eip_home



 


 
 
