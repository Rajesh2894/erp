--liquibase formatted sql
--changeset nilima:V20171031172712__AL_tb_tax_mas_12102017.sql
update tb_tax_mas  set CREATED_DATE='2017-10-07 16:02:01' where CREATED_DATE is null;
commit;

--liquibase formatted sql
--changeset nilima:V20171031172712__AL_tb_tax_mas_121020171.sql
update tb_tax_mas a  set CREATED_BY=(select empid from employee where EMPLOGINNAME='MBA' and orgid=a.orgid)
where CREATED_BY is null;
commit;

--liquibase formatted sql
--changeset nilima:V20171031172712__AL_tb_tax_mas_121020172.sql
ALTER TABLE tb_tax_mas
CHANGE COLUMN DP_DEPTID DP_DEPTID BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN ORGID ORGID BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN CREATED_BY CREATED_BY BIGINT(12) NOT NULL COMMENT '' ,
CHANGE COLUMN CREATED_DATE CREATED_DATE DATETIME NOT NULL COMMENT '' ,
CHANGE COLUMN tax_desc_id tax_desc_id INT(12) NOT NULL COMMENT 'Tax Desc Id from \'TXN\' prefix' ;



