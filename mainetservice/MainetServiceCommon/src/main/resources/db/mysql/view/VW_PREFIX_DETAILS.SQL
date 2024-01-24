CREATE OR REPLACE VIEW `VW_PREFIX_DETAILS` AS 
select `m`.`CPM_ID` AS `cpm_id`,`m`.`CPM_PREFIX` AS `cpm_prefix`,`m`.`CPM_DESC` AS `cpm_desc`,coalesce(`m`.`CPM_TYPE`,'N') AS `cpm_type`,
`pm`.`COM_ID` AS `com_id`,`pm`.`COM_LEVEL` AS `com_level`,`pm`.`COM_VALUE` AS `com_value`,`pm`.`COM_DESC` AS `com_desc`,
`pm`.`COM_DESC_MAR` AS `com_desc_mar`,`pd`.`COD_ID` AS `COD_CPD_ID`,`pd`.`COD_VALUE` AS `COD_CPD_VALUE`,
NULL AS `cpd_others`,`pd`.`COD_DESC` AS `COD_CPD_DESC`,`pd`.`COD_DESC_MAR` AS `COD_CPD_DESC_MAR`,
`pd`.`CPD_DEFAULT` AS `cod_cpd_default`,`pd`.`PARENT_ID` AS `COD_CPD_PARENT_ID`,`o`.`ORGID` AS `orgid`,
`m`.`CPM_REPLICATE_FLAG` AS `cpm_replicate_flag` from (((`TB_COMPARAM_MAS` `m` join `TB_COMPARENT_MAS` `pm`) 
join `TB_COMPARENT_DET` `pd`) join `TB_ORGANISATION` `o`) 
where ((`m`.`CPM_ID` = `pm`.`CPM_ID`) and (coalesce(`m`.`CPM_TYPE`,'N') = 'H') and (`m`.`CPM_REPLICATE_FLAG` = 'N') 
and (`m`.`CPM_STATUS` = 'A') and (`pm`.`COM_ID` = `pd`.`COM_ID`) and (`pm`.`ORGID` = `pd`.`ORGID`) and 
(`pm`.`COM_STATUS` = 'Y') and (`pd`.`COD_STATUS` = 'Y') and (`pd`.`ORGID` = `o`.`ORGID`) and (`o`.`DEFAULT_STATUS` = 'Y')
and (`o`.`ORG_STATUS` = 'A')) union all 
select `m`.`CPM_ID` AS `cpm_id`,`m`.`CPM_PREFIX` AS `cpm_prefix`,`m`.`CPM_DESC` AS `cpm_desc`,
coalesce(`m`.`CPM_TYPE`,'N') AS `cpm_type`,`pm`.`COM_ID` AS `com_id`,`pm`.`COM_LEVEL` AS `com_level`,
`pm`.`COM_VALUE` AS `com_value`,`pm`.`COM_DESC` AS `com_desc`,`pm`.`COM_DESC_MAR` AS `com_desc_mar`,
`pd`.`COD_ID` AS `COD_CPD_ID`,`pd`.`COD_VALUE` AS `COD_CPD_VALUE`,NULL AS `cpd_others`,`pd`.`COD_DESC` AS `COD_CPD_DESC`,
`pd`.`COD_DESC_MAR` AS `COD_CPD_DESC_MAR`,`pd`.`CPD_DEFAULT` AS `cod_cpd_default`,`pd`.`PARENT_ID` AS `COD_CPD_PARENT_ID`,
`o`.`ORGID` AS `orgid`,`m`.`CPM_REPLICATE_FLAG` AS `cpm_replicate_flag` from (((`TB_COMPARAM_MAS` `m` join `TB_COMPARENT_MAS` `pm`) join
`TB_COMPARENT_DET` `pd`) join `TB_ORGANISATION` `o`) where ((`m`.`CPM_ID` = `pm`.`CPM_ID`) and
(coalesce(`m`.`CPM_TYPE`,'N') = 'H') and (`m`.`CPM_REPLICATE_FLAG` = 'Y') and (`m`.`CPM_STATUS` = 'A') and
(`pm`.`COM_ID` = `pd`.`COM_ID`) and (`pm`.`ORGID` = `pd`.`ORGID`) and (`pm`.`COM_STATUS` = 'Y') and
(`pd`.`COD_STATUS` = 'Y') and (`pd`.`ORGID` = `o`.`ORGID`) and (`o`.`ORG_STATUS` = 'A')) 
union all select `m`.`CPM_ID` AS `cpm_id`,`m`.`CPM_PREFIX` AS `cpm_prefix`,`m`.`CPM_DESC` AS `cpm_desc`,
coalesce(`m`.`CPM_TYPE`,'N') AS `cpm_type`,NULL AS `com_id`,NULL AS `com_level`,NULL AS `com_value`,NULL AS `com_desc`,
NULL AS `com_desc_mar`,`cd`.`CPD_ID` AS `cpd_id`,`cd`.`CPD_VALUE` AS `cpd_value`,`cd`.`CPD_OTHERS` AS `cpd_others`,
`cd`.`CPD_DESC` AS `cpd_desc`,`cd`.`CPD_DESC_MAR` AS `cpd_desc_mar`,`cd`.`CPD_DEFAULT` AS `cod_cpd_default`,
NULL AS `parent_id`,`o`.`ORGID` AS `orgid`,`m`.`CPM_REPLICATE_FLAG` AS `cpm_replicate_flag` 
from ((`TB_COMPARAM_MAS` `m` join `TB_COMPARAM_DET` `cd`) join `TB_ORGANISATION` `o`) where ((`m`.`CPM_ID` = `cd`.`CPM_ID`) 
and (coalesce(`m`.`CPM_TYPE`,'N') <> 'H') and (`m`.`CPM_STATUS` = 'A') and (`m`.`CPM_REPLICATE_FLAG` = 'N') 
and (`cd`.`ORGID` = `o`.`ORGID`) and (`cd`.`CPD_STATUS` = 'A') and (`o`.`DEFAULT_STATUS` = 'Y') and (`o`.`ORG_STATUS` = 'A')) 
union all select `m`.`CPM_ID` AS `cpm_id`,`m`.`CPM_PREFIX` AS `cpm_prefix`,`m`.`CPM_DESC` AS `cpm_desc`,
coalesce(`m`.`CPM_TYPE`,'N') AS `cpm_type`,NULL AS `com_id`,NULL AS `com_level`,NULL AS `com_value`,
NULL AS `com_desc`,NULL AS `com_desc_mar`,`cd`.`CPD_ID` AS `cpd_id`,`cd`.`CPD_VALUE` AS `cpd_value`,
`cd`.`CPD_OTHERS` AS `cpd_others`,`cd`.`CPD_DESC` AS `cpd_desc`,`cd`.`CPD_DESC_MAR` AS `cpd_desc_mar`,
`cd`.`CPD_DEFAULT` AS `cod_cpd_default`,NULL AS `parent_id`,`o`.`ORGID` AS `orgid`,
`m`.`CPM_REPLICATE_FLAG` AS `cpm_replicate_flag` from ((`TB_COMPARAM_MAS` `m` join `TB_COMPARAM_DET` `cd`) join `TB_ORGANISATION` `o`) 
where ((`m`.`CPM_ID` = `cd`.`CPM_ID`) and (coalesce(`m`.`CPM_TYPE`,'N') = 'N') and (`m`.`CPM_STATUS` = 'A') and (`m`.`CPM_REPLICATE_FLAG` = 'Y') and
(`cd`.`CPD_STATUS` = 'A') and (`cd`.`ORGID` = `o`.`ORGID`) and (`o`.`ORG_STATUS` = 'A'));
