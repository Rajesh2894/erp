--liquibase formatted sql
--changeset nilima:V20170419123355__AL_TB_DOCUMENT_GRUOP.sql
declare
  n_cnt number(1);
  str varchar2(200);
begin
  select count(1) into n_cnt from user_objects k where k.OBJECT_NAME='TB_DOCUMENT_GRUOP';
   
  
  if n_cnt = 1 then
     str :='drop table TB_DOCUMENT_GRUOP;'; 
     execute immediate str;
  end if;
end;
/
