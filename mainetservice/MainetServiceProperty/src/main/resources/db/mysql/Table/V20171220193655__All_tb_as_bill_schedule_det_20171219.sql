--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712191.sql
alter table tb_as_bill_schedule_det modify  BILL_FROM_DATE datetime;
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712192.sql
alter table tb_as_bill_schedule_det modify  BILL_to_DATE datetime;
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712193.sql
alter table tb_as_bill_schedule_mast drop as_frequency_from;
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712194.sql
alter table tb_as_bill_schedule_mast drop as_frequency_to;
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712195.sql
ALTER TABLE tb_as_bill_schedule_mast CHANGE COLUMN as_aut_by as_aut_by INT(11) null; 
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712196.sql
ALTER TABLE tb_as_bill_schedule_mast add COLUMN  as_bill_status char(1);
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712197.sql
ALTER TABLE tb_as_bill_schedule_mast CHANGE COLUMN as_aut_status as_aut_status char(1); 
--liquibase formatted sql
--changeset nilima:V20171220193655__All_tb_as_bill_schedule_det_201712199.sql
ALTER TABLE tb_as_bill_schedule_mast CHANGE COLUMN as_aut_date as_aut_date datetime; 
