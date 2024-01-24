update tb_workflow_request a set a.STATUS='CLOSED',a.LAST_DATE_OF_ACTION=now(),a.UPDATED_DATE=now(),
a.UPDATED_BY=1 
where a.STATUS='PENDING';
commit;
update tb_workflow_request a set a.PROCESS_SESSIONID=-1;
commit;


