--liquibase formatted sql
--changeset Kanchan:V20230628101201__AL_mm_invoice_overheads_28062023.sql
alter table mm_invoice_overheads
modify column description varchar(100) null default null,
modify column overheadtype char(1) null default null,
modify column amount double(12,2) null default null;