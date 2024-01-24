ALTER TABLE alf_store 
DROP FOREIGN KEY fk_alf_store_root;
ALTER TABLE alf_store 
DROP INDEX fk_alf_store_root ;
drop table alf_usage_delta;
drop table alf_tenant;
drop table alf_subscriptions;
drop table alf_prop_unique_ctx;
drop table alf_prop_string_value;
drop table alf_prop_serializable_value;
drop table alf_lock;
drop table alf_prop_link;
drop table alf_prop_double_value;
drop table alf_prop_date_value;
drop table alf_prop_class;
drop table alf_node_properties;
drop table alf_node_assoc;
drop table alf_node_aspects;
drop table alf_content_url_encryption;
drop table alf_child_assoc;
drop table alf_auth_status;
drop table alf_authority_alias;
drop table alf_authority;
drop table alf_audit_model;
drop table alf_audit_entry;
drop table alf_audit_app;
drop table alf_applied_patch;
drop table alf_activity_post;
drop table alf_activity_feed_control;
drop table alf_activity_feed;
drop table alf_acl_member;
drop table alf_ace_context;
drop table alf_access_control_entry;
drop table act_ru_variable;
drop table act_ru_task;
drop table act_ru_job;
drop table act_ru_identitylink;
drop table act_ru_execution;
drop table act_ru_event_subscr;
drop table act_re_procdef;
drop table act_re_model;
drop table act_re_deployment;
drop table act_procdef_info;
drop table act_id_user;
drop table act_id_membership;
drop table act_id_info;
drop table act_id_group;
drop table act_hi_varinst;
drop table act_hi_taskinst;
drop table act_hi_procinst;
drop table act_hi_identitylink;
drop table act_hi_detail;
drop table act_hi_comment;
drop table act_hi_attachment;
drop table act_hi_actinst;
drop table act_ge_property;
drop table act_ge_bytearray;
drop table act_evt_log;
drop table alf_prop_value;
drop table alf_prop_root;
drop table alf_permission;
drop table alf_mimetype;
drop table alf_lock_resource;
drop table alf_encoding;
drop table alf_content_url;
drop table alf_content_data;
drop table alf_node;
drop table alf_store;
drop table alf_locale;
drop table alf_qname;
drop table alf_namespace;
drop table alf_transaction;
drop table alf_server;
drop table alf_access_control_list;
drop table alf_acl_change_set;