--liquibase formatted sql
--changeset PramodPatil:V20231205141343__AL_tb_emp_ward_zone_det_05122023.sql
Alter table tb_emp_ward_zone_det add column LOC1 varchar(4000) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231205141343__AL_tb_emp_ward_zone_det_051220231.sql
Alter table tb_emp_ward_zone_det add column LOC2 varchar(4000) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231205141343__AL_tb_emp_ward_zone_det_051220232.sql
Alter table tb_emp_ward_zone_det add column LOC3 varchar(4000) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231205141343__AL_tb_emp_ward_zone_det_051220233.sql
Alter table tb_emp_ward_zone_det add column LOC4 varchar(4000) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231205141343__AL_tb_emp_ward_zone_det_051220234.sql
Alter table tb_emp_ward_zone_det add column LOC5 varchar(3000) null default null;