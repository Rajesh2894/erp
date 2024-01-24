DELETE FROM tb_care_feedback;
DELETE FROM tb_care_department_action;
DELETE FROM tb_care_request;
DELETE FROM tb_workflow_action;
DELETE FROM tb_workflow_task; 
DELETE FROM tb_workflow_request;
commit;

delete FROM _sequences where name in
(select SQ_SEQ_NAME from tb_java_seq_generation where SQ_TBL_NAME in
('tb_care_feedback','tb_care_department_action','tb_care_request','tb_workflow_action','tb_workflow_request','tb_workflow_task'));
commit;

delete from tb_java_seq_generation where SQ_TBL_NAME in
('tb_care_feedback','tb_care_department_action','tb_care_request','tb_workflow_action','tb_workflow_request','tb_workflow_task');
commit;