--liquibase formatted sql
--changeset nilima:UP_tb_comparent_mas_07122017.sql
update tb_comparent_mas j  set J.COM_STATUS='Y' where j.com_status is null;
COMMIT;