--liquibase formatted sql
--changeset Kanchan:
alter table exception_log_dtl modify column exception_class varchar(100);
--liquibase formatted sql
--changeset Kanchan: 
alter table exception_log_dtl modify column  URL  varchar(100);
--liquibase formatted sql
--changeset Kanchan:
alter table exception_log_dtl modify column file_name  varchar(50);
--liquibase formatted sql
--changeset Kanchan:
alter table exception_log_dtl modify column method_name   varchar(50
