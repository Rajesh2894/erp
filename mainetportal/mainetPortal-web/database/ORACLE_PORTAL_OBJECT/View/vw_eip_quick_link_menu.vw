create or replace view vw_eip_quick_link_menu as
(select rownum row_num, x."ORGID",x."LINKID",x."LINK_ID",x."MENU_NM_EN",x."MENU_NM_REG",x."PARENTID",
   x."LINK_TYPE",x."HAS_SUB_LINK",x.PAGE_URL,x."SORT_ORDER",x."LINK_ORDER",X."IS_LINK_MODIFY"
from
(select s.orgid,
       link_id linkid,
       'M' || link_id link_id,
       link_title_en MENU_NM_EN,
       link_title_reg MENU_NM_REG,
       'M' parentid,
       'M' link_type,
       null has_sub_link,
--       null page_url,--commened by yv dated 130514
       s.link_path page_url, --added on 160514
       to_number(link_id) sort_order,
       link_order,
       null  Section_type, --added by yv dated 130514
       null  img_link_type, --added by yv dated 140514
       S.IS_LINK_MODIFY  IS_LINK_MODIFY ---ADDED A
  from tb_eip_links_master s where s.isdeleted='N'
  union
select s.orgid,
       d.sub_link_mas_id linkid,
       'F' || d.sub_link_mas_id link_id,
       d.sub_link_name_en MENU_NM_EN,
       d.sub_link_name_rg MENU_NM_REG,
       case
         when d.sub_link_par_id is null then
          'M' || d.link_id
         else
          'F' || d.sub_link_par_id
       end parentid,
       'F' link_type,
       d.has_sub_link,
       d.page_url,
--       to_number(s.link_id || '.' || d.sub_link_mas_id) sort_order, --commented by yv dated 200514
      -- to_number(s.link_id || '.' || d.sub_link_order) sort_order, --added by yv dated 200514
         to_number(s.link_id || d.sub_link_order) sort_order, --added by Rajendra dated 110614
       link_order,
       fn_getcpddesc_eip( d.cpd_secion_type,'V',d.orgid) Section_type, --added by yv dated 130514
       fn_getcpddesc_eip( d.cpd_img_link_type,'V',d.orgid) IMG_link_type, --added by yv dated 130514
       D.IS_LINK_MODIFY IS_LINK_MODIFY
  from tb_eip_links_master s, TB_EIP_SUB_LINKS_MASTER d
 where s.link_id = d.link_id
 and s.isdeleted='N' and d.isdeleted = 'N'
   and s.orgid = d.orgid
   order by LINK_ORDER,SORT_ORDER) x
   );
