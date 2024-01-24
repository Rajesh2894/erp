CREATE or replace view vw_eip_quick_link_menu as
select `FN_GET_ROWNUM`() AS `ROW_NUM`,`s`.`ORGID` AS `ORGID`,`s`.`LINK_ID` AS `LINKID`,concat('M',
cast(`s`.`LINK_ID` as char charset utf8)) AS `LINK_ID`,`s`.`LINK_TITLE_EN` AS `MENU_NM_EN`,
`s`.`LINK_TITLE_REG` AS `MENU_NM_REG`,'M' AS `PARENTID`,'M' AS `LINK_TYPE`,NULL AS `HAS_SUB_LINK`,
`s`.`LINK_PATH` AS `PAGE_URL`,`s`.`LINK_ID` AS `SORT_ORDER`,`s`.`LINK_ORDER` AS `LINK_ORDER`,NULL AS `SECTION_TYPE`,
NULL AS `IMG_LINK_TYPE`,`s`.`IS_LINK_MODIFY` AS `IS_LINK_MODIFY`,`s`.`CHEKER_FLAG` AS `Cheker` 
from `tb_eip_links_master` `s` 
where (`s`.`ISDELETED` = 'N') 
union 
select `FN_GET_ROWNUM`() AS `ROW_NUM`,`s`.`ORGID` AS `ORGID`,`d`.`SUB_LINK_MAS_ID` AS `LINKID`,
concat('F',cast(`d`.`SUB_LINK_MAS_ID` as char charset utf8)) AS `LINK_ID`,`d`.`SUB_LINK_NAME_EN` AS `MENU_NM_EN`,
`d`.`SUB_LINK_NAME_RG` AS `MENU_NM_REG`,(case when isnull(`d`.`SUB_LINK_PAR_ID`) then concat('M',cast(`d`.`LINK_ID` as char charset utf8))
 else concat('F',cast(`d`.`SUB_LINK_PAR_ID` as char charset utf8)) end) AS `PARENTID`,'F' AS `LINK_TYPE`,`d`.
 `HAS_SUB_LINK` AS `HAS_SUB_LINK`,`d`.`PAGE_URL` AS `PAGE_URL`,`d`.`SUB_LINK_ORDER` AS `SORT_ORDER`,
 `s`.`LINK_ORDER` AS `LINK_ORDER`,`FN_GETCPDDESC`(`d`.`CPD_SECION_TYPE`,'V',`d`.`ORGID`) AS `SECTION_TYPE`,
 `FN_GETCPDDESC`(`d`.`CPD_IMG_LINK_TYPE`,'V',`d`.`ORGID`) AS `IMG_LINK_TYPE`,`d`.`IS_LINK_MODIFY` AS `IS_LINK_MODIFY`,
 `s`.`CHEKER_FLAG` AS `Cheker` 
 from (`tb_eip_links_master` `s` join `tb_eip_sub_links_master` `d`) 
 where ((`s`.`LINK_ID` = `d`.`LINK_ID`) and (`s`.`ISDELETED` = 'N') and (`d`.`ISDELETED` = 'N') and (`s`.`ORGID` = `d`.`ORGID`) 
 and (`s`.`CHEKER_FLAG` = 'Y') and (`d`.`CHEKER_FLAG` = 'Y')) order by `ROW_NUM`,`LINK_ORDER`,`SORT_ORDER`;