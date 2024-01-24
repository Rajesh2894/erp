package com.abm.mainet.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cms.dao.IVIEWQuickLinkDAO;
import com.abm.mainet.cms.domain.VIEWQuickLink;
import com.abm.mainet.cms.dto.VIEWQuickLinkDTO;
import com.abm.mainet.cms.mapper.ViewQuickLinkMapper;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.IMenuDTO;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author pabitra.raulo
 *
 */
@Service
public class VIEWQuickLinkService implements IVIEWQuickLinkService {

    private static final String M = "m";
    
    @Autowired
    IVIEWQuickLinkDAO viewQuickLinkDAO;

    private String quickLinkMenu = MainetConstants.operator.EMPTY;
    
    @Autowired
    private ViewQuickLinkMapper viewQuickLinkMapper;
    @Override
    @Transactional(readOnly = true)
    public List<VIEWQuickLink> getQuickLinkList(Organisation org) {
    	return viewQuickLinkDAO.getallQuicklink(org);
	}
    @Override
    @Transactional(readOnly = true)
	public List<VIEWQuickLink> getQuickLinkListSuda(final Organisation org) {
		List<VIEWQuickLink> viewQuickLinkList = new ArrayList<>();
		List<Object[]> viewQuickLinkArr = viewQuickLinkDAO.getallQuicklinkSuda(org);
		if (viewQuickLinkArr != null) {
			for (Object[] objArr : viewQuickLinkArr) {
				VIEWQuickLink vwQuick=new VIEWQuickLink();
				if(objArr[0]!=null)
				vwQuick.setId(Long.valueOf(objArr[0].toString()));
				if(objArr[1]!=null)
					vwQuick.setChecker(objArr[1].toString());
				if(objArr[2]!=null)
					vwQuick.setHaSubLink(objArr[2].toString());
				if(objArr[3]!=null)
					vwQuick.setIsLinkModify(objArr[3].toString());
				if(objArr[4]!=null)
					vwQuick.setLinkOrder(Double.valueOf(objArr[4].toString()));
				if(objArr[5]!=null)
					vwQuick.setLink_id(objArr[5].toString());
				if(objArr[6]!=null)
					vwQuick.setLink_type(objArr[6].toString());
				if(objArr[7]!=null)
					vwQuick.setLinkid(Integer.valueOf(objArr[7].toString()));
				if(objArr[8]!=null)
					vwQuick.setMenu_name_eng(objArr[8].toString());
				if(objArr[9]!=null)
					vwQuick.setMenu_name_reg(objArr[9].toString());
				if(objArr[10]!=null)
					vwQuick.setOrgid(Long.valueOf(objArr[10].toString()));
				if(objArr[11]!=null)
					vwQuick.setPage_url(objArr[11].toString());
				if(objArr[12]!=null)
					vwQuick.setParent_id(objArr[12].toString());
				if(objArr[13]!=null)
					vwQuick.setSort_order(Long.valueOf(objArr[13].toString()));
				viewQuickLinkList.add(vwQuick);
			}
		}
		return viewQuickLinkList;
	}
   
    @Override
    @Transactional(readOnly = true)
    public String getQuickLink(final Organisation org, final CsrfToken token, int languageId) {

        quickLinkMenu = new String();

        List<VIEWQuickLink> viewQuickLinkList = null;

		if (org != null) {
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SUDA)) {
				viewQuickLinkList = getQuickLinkListSuda(org);
			} else {
				viewQuickLinkList = getQuickLinkList(org);
			}
		}

        if (viewQuickLinkList == null) {
            viewQuickLinkList = new ArrayList<>();
        }

        final List<VIEWQuickLink> listContainerQuickLinks = prepareParentMenuList(viewQuickLinkList);// Parent And Child Node
                                                                                                     // Preparation

        quickLinkMenu = prepareMenuString(listContainerQuickLinks.stream().filter(i-> !(i.getMenu_name_eng().trim()).equalsIgnoreCase(MainetConstants.MENU.Smart_City_Label)).collect(Collectors.toList()), token, languageId);// Prepare Quick link MenuString
        return quickLinkMenu;
    }

    

	@Override
    public String getQuickLinkforSamrtcity(final Organisation org, final CsrfToken token) {

        quickLinkMenu = new String();

        List<VIEWQuickLink> viewQuickLinkList = null;

        if (org != null) {
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SUDA)) {
				viewQuickLinkList = getQuickLinkListSuda(org);
			} else {
				viewQuickLinkList = getQuickLinkList(org);
			}
		}

        if (viewQuickLinkList == null) {
            viewQuickLinkList = new ArrayList<>();
        }

        final List<VIEWQuickLink> listContainerQuickLinks = prepareParentMenuListforSmartCity(viewQuickLinkList);// Parent And
                                                                                                                 // Child Node
                                                                                                                 // Preparation
        final List<VIEWQuickLink> listContainerQuickLinks2 = new ArrayList<>();
        if (listContainerQuickLinks != null) {
            for (final VIEWQuickLink listContainerQuickLinks1 : listContainerQuickLinks) {
                if (listContainerQuickLinks1.getMenu_name_eng().equalsIgnoreCase("Smart City")) {
                    listContainerQuickLinks1.setPage_url("smartcity.html?edit");
                    final List<VIEWQuickLink> listContainerQuickLink2 = listContainerQuickLinks1.getChild();
                    if (listContainerQuickLink2 != null) {
                        for (final VIEWQuickLink listContainerQuickLinkchild : listContainerQuickLink2) {
                            listContainerQuickLinkchild.setPage_url("smartcity.html?edit");

                            listContainerQuickLinks2.add(listContainerQuickLinkchild);

                        }
                    }
                }
            }
        }
        quickLinkMenu = prepareMenuStringSmartcity(listContainerQuickLinks2, token);// Prepare Quick link MenuString
        return quickLinkMenu;
    }

    @Transactional(readOnly = true)
    private List<VIEWQuickLink> prepareParentMenuListforSmartCity(final List<VIEWQuickLink> listQuickLinks) {

        final List<VIEWQuickLink> listContainerQuickLinks = new ArrayList<>();

        CopyOnWriteArrayList<VIEWQuickLink> list = null;

        VIEWQuickLink viewQuickLink = null;

        VIEWQuickLink viewQuickLink2 = null;

        for (final ListIterator<VIEWQuickLink> lt = listQuickLinks.listIterator(); lt.hasNext();) {
            viewQuickLink = lt.next();
            list = new CopyOnWriteArrayList<>();

            for (final ListIterator<VIEWQuickLink> listIterator = listQuickLinks.listIterator(); listIterator.hasNext();) {
                viewQuickLink2 = listIterator.next();

                if (viewQuickLink2.getParent_id().equalsIgnoreCase(viewQuickLink.getLink_id())) {
                    list.add(viewQuickLink2);

                    viewQuickLink.setChild(list);
                    lt.set(viewQuickLink);

                    viewQuickLink2.setParent(viewQuickLink2);
                    listIterator.set(viewQuickLink2);
                }
            }

            if (viewQuickLink.getLink_type().equalsIgnoreCase(M) && viewQuickLink.getParent_id().equalsIgnoreCase(M)) {
                listContainerQuickLinks.add(viewQuickLink);
            }
        }

        return listContainerQuickLinks;// This list is populate with desired elements in the @method:{findChildForParents}
    }

    @Transactional(readOnly = true)
    private List<VIEWQuickLink> prepareParentMenuList(final List<VIEWQuickLink> listQuickLinks) {

        final List<VIEWQuickLink> listContainerQuickLinks = new ArrayList<>();

        CopyOnWriteArrayList<VIEWQuickLink> list = null;

        VIEWQuickLink viewQuickLink = null;

        VIEWQuickLink viewQuickLink2 = null;
        for (final ListIterator<VIEWQuickLink> lt = listQuickLinks.listIterator(); lt.hasNext();) {
            viewQuickLink = lt.next();
            viewQuickLink.getPage_url();

            list = new CopyOnWriteArrayList<>();

            for (final ListIterator<VIEWQuickLink> listIterator = listQuickLinks.listIterator(); listIterator.hasNext();) {
                viewQuickLink2 = listIterator.next();
                if (!viewQuickLink.getMenu_name_eng().contains("Smart City")) {
                    if (viewQuickLink2.getParent_id().equalsIgnoreCase(viewQuickLink.getLink_id())) {
                        list.add(viewQuickLink2);

                        viewQuickLink.setChild(list);
                        lt.set(viewQuickLink);

                        viewQuickLink2.setParent(viewQuickLink2);
                        listIterator.set(viewQuickLink2);
                    }
                }
            }

            if (viewQuickLink.getLink_type().equalsIgnoreCase(M) && viewQuickLink.getParent_id().equalsIgnoreCase(M)) {
                listContainerQuickLinks.add(viewQuickLink);
            }
        }

        return listContainerQuickLinks;// This list is populate with desired elements in the @method:{findChildForParents}
    }

    private String prepareMenuStringSmartcity(final List<VIEWQuickLink> listContainerQuickLinks2, final CsrfToken token) {
        final StringBuffer html = new StringBuffer();
        VIEWQuickLink viewQuickLink = null;

        if (listContainerQuickLinks2 == null) {

            return html.toString();

        } else {

            if (listContainerQuickLinks2.size() > 0) {

                for (final ListIterator<VIEWQuickLink> iterator = listContainerQuickLinks2.listIterator(); iterator.hasNext();) {

                    viewQuickLink = iterator.next();
                    if ((viewQuickLink.getChild() != null)
                            && (viewQuickLink.getChild().size() > 0)) {
                        if (viewQuickLink.getIsLinkModify().equals(MainetConstants.MENU.F)) {
                            html.append(IMenuDTO.MENU_LI_PARENT);
                        } else {
                            html.append(IMenuDTO.MENU_LI_PARENT_NEW);
                        }
                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                        html.append(IMenuDTO.CLOSE_TAG);

                        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                            html.append(viewQuickLink.getMenu_name_eng());
                        } else {
                            html.append(viewQuickLink.getMenu_name_reg());
                        }

                        html.append(IMenuDTO.MENU_ANCHOR_CLOSE);

                        html.append(IMenuDTO.MENU_UL_PARENT);
                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                        html.append(IMenuDTO.CLOSE_TAG);
                        html.append(prepareMenuStringforSmartcitychild(viewQuickLink.getChild(), token));
                    }

                    else if (viewQuickLink != null) {

                        if (viewQuickLink.getIsLinkModify().equals(MainetConstants.MENU.F)) {
                            html.append(IMenuDTO.MENU_LI_OPEN);
                        } else {
                            html.append(IMenuDTO.MENU_LI_OPEN_NEW);
                        }

                        html.append("<form action='smartcity.html?edit=" + viewQuickLink.getLinkid() + "' method='post' name='frm"
                                + viewQuickLink.getLinkid() + "'");
                        html.append(" id='frm" + viewQuickLink.getLinkid() + "'>");
                        html.append("<input type='hidden' name='_csrf'  value='" + token.getToken() + "' />");

                        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                            if (viewQuickLink.getPage_url().split("://").length > 1) {
                                html.append("<a><input type='submit'   value='" + viewQuickLink.getMenu_name_eng() + "' /> </a>");
                            } else {
                                html.append("<a><input type='submit'  value='" + viewQuickLink.getMenu_name_eng() + "' /> </a>");
                            }
                        } else {
                            if (viewQuickLink.getPage_url().split("://").length > 1) {
                                html.append("<a><input type='submit'  value='" + viewQuickLink.getMenu_name_reg() + "' /> </a>");
                            } else {
                                html.append("<a> <input type='submit'  value='" + viewQuickLink.getMenu_name_reg() + "' /> </a>");
                            }
                        }

                        html.append(" <input type=\"hidden\" name=\"rowId\" id=\"rowId" + viewQuickLink.getLinkid());
                        html.append("\" value='" + viewQuickLink.getLinkid() + "'/>");
                        html.append("</form>");

                    }

                }
                html.append(IMenuDTO.MENU_LI_CLOSE);
            }
        }
        return html.toString();
    }

    private String prepareMenuStringforSmartcitychild(final List<VIEWQuickLink> listQuickLinks, final CsrfToken token) {
        final StringBuffer html = new StringBuffer();
        VIEWQuickLink viewQuickLinkref = null;
        VIEWQuickLink viewQuickLink = null;

        if (listQuickLinks == null) {

            return html.toString();

        } else {

            if (listQuickLinks.size() > 0) {

                for (final ListIterator<VIEWQuickLink> iterator = listQuickLinks.listIterator(); iterator.hasNext();) {

                    viewQuickLink = iterator.next();
                    viewQuickLinkref = viewQuickLink;

                    if ((viewQuickLink.getChild() != null)
                            && (viewQuickLink.getChild().size() > 0)) {
                        if (viewQuickLink.getIsLinkModify().equals(MainetConstants.MENU.F)) {
                            html.append(IMenuDTO.MENU_LI_PARENT);
                        } else {
                            html.append(IMenuDTO.MENU_LI_PARENT_NEW);
                        }
                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                        html.append(IMenuDTO.CLOSE_TAG);

                        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                            html.append(viewQuickLink.getMenu_name_eng());
                        } else {
                            html.append(viewQuickLink.getMenu_name_reg());
                        }

                        html.append(IMenuDTO.MENU_ANCHOR_CLOSE);

                        html.append(IMenuDTO.MENU_UL_PARENT);
                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                        html.append(IMenuDTO.CLOSE_TAG);
                        html.append(prepareMenuStringforSmartcitychild(viewQuickLink.getChild(), token));
                    } else {

                        if (viewQuickLink != null) {

                            if (viewQuickLink.getIsLinkModify().equals(MainetConstants.MENU.F)) {
                                html.append(IMenuDTO.MENU_LI_OPEN);
                            } else {
                                html.append(IMenuDTO.MENU_LI_OPEN_NEW);
                            }

                            if ((viewQuickLink.getParent_id() != null)
                                    && (viewQuickLink.getLink_type() != null)
                                    && (viewQuickLink.getParent_id().indexOf(MainetConstants.MENU.F) != -1)
                                    && viewQuickLink.getLink_type().equalsIgnoreCase("f") && (viewQuickLink.getPage_url() != null)
                                    && viewQuickLink.getPage_url()
                                            .equalsIgnoreCase(MainetConstants.EIPSection.DEFAULT_PAGE_URL)) {
                                html.append("<form action='smartcity.html?edit=" + viewQuickLink.getLinkid()
                                        + "' method='post' name='frm" + viewQuickLink.getLinkid() + "'");
                                html.append(" id='frm" + viewQuickLink.getLinkid() + "'>");
                                html.append("<input type='hidden' name='_csrf'  value='" + token.getToken() + "' />");

                                if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                                    if (viewQuickLink.getPage_url().split("://").length > 1) {
                                        html.append("<input type='submit'  value='" + viewQuickLink.getMenu_name_eng() + "' />");
                                    } else {
                                        html.append("<input type='submit'  value='" + viewQuickLink.getMenu_name_eng() + "' />");
                                    }
                                } else {
                                    if (viewQuickLink.getPage_url().split("://").length > 1) {
                                        html.append("<input type='submit'  value='" + viewQuickLink.getMenu_name_reg() + " ' />");
                                    } else {
                                        html.append("<input type='submit'  value='" + viewQuickLink.getMenu_name_reg() + "' />");
                                    }
                                }

                                html.append("<input type=\"hidden\" name=\"rowId\" id=\"rowId" + viewQuickLink.getLinkid());
                                html.append("\" value='" + viewQuickLink.getLinkid() + "'/>");
                                html.append("</form>");
                            } else if ((viewQuickLink.getParent_id() != null)
                                    && (viewQuickLink.getLink_type() != null) && (viewQuickLink.getParent_id().indexOf("M") != -1)
                                    && viewQuickLink.getLink_type().equalsIgnoreCase(MainetConstants.MENU.F)) {

                                if ((viewQuickLink.getHaSubLink() != null) && viewQuickLink.getHaSubLink().equalsIgnoreCase("N")
                                        && (viewQuickLink.getPage_url() != null) && viewQuickLink.getPage_url()
                                                .equalsIgnoreCase(MainetConstants.EIPSection.DEFAULT_PAGE_URL)) {
                                    html.append("<form action='smartcity.html?edit=" + viewQuickLink.getLinkid()
                                            + "' method='post' name='frm" + viewQuickLink.getLinkid() + "'");
                                    html.append(" id='frm" + viewQuickLink.getLinkid() + "'>");
                                    html.append("<input type='hidden' name='_csrf'  value='" + token.getToken() + "' />");
                                    if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                                        if (viewQuickLink.getPage_url().split("://").length > 1) {
                                            html.append(
                                                    "<input type='submit'  value='" + viewQuickLink.getMenu_name_eng() + " ' />");
                                        } else {
                                            html.append(
                                                    "<input type='submit'  value='" + viewQuickLink.getMenu_name_eng() + " ' />");
                                        }
                                    } else {
                                        if (viewQuickLink.getPage_url().split("://").length > 1) {
                                            html.append(
                                                    "<input type='submit'  value='" + viewQuickLink.getMenu_name_reg() + " ' />");
                                        } else {
                                            html.append(
                                                    "<input type='submit'  value='" + viewQuickLink.getMenu_name_reg() + " ' />");
                                        }
                                    }

                                    html.append("<input type=\"hidden\" name=\"rowId\" id=\"rowId" + viewQuickLink.getLinkid());
                                    html.append("\" value='" + viewQuickLink.getLinkid() + "'/>");
                                    html.append("</form>");
                                } else {
                                    html.append(IMenuDTO.MENU_ANCHOR_OPEN);
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append(viewQuickLink.getPage_url());
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                    if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                                        html.append(IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_eng()
                                                + IMenuDTO.MENU_ANCHOR_CLOSE);
                                    } else {
                                        html.append(IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_reg()
                                                + IMenuDTO.MENU_ANCHOR_CLOSE);
                                    }
                                }
                            }

                            else if ((viewQuickLink.getParent_id() != null)
                                    && (viewQuickLink.getLink_type() != null)
                                    && viewQuickLink.getParent_id().equalsIgnoreCase("M")
                                    && viewQuickLink.getLink_type().equalsIgnoreCase("M")) {

                                if (viewQuickLink.getPage_url().split("://").length > 1) {
                                    html.append(IMenuDTO.MENU_ANCHOR_OPEN);
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append(viewQuickLink.getPage_url());
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                    html.append("target='_blank'");
                                    
                                    /*html.append("#");                                    
                                    html.append("onclick=return");
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append("showConfirm('" + viewQuickLink.getPage_url() + "');");
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);*/
                                } else {
                                    html.append(IMenuDTO.MENU_ANCHOR_OPEN);
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append(viewQuickLink.getPage_url());
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                }

                                if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
                                    html.append("class='extlink'");
                                    html.append(
                                            IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_eng() + IMenuDTO.MENU_ANCHOR_CLOSE);
                                } else {
                                    html.append("class='extlink'");
                                    html.append(
                                            IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_reg() + IMenuDTO.MENU_ANCHOR_CLOSE);
                                }

                            }
                            html.append(IMenuDTO.MENU_LI_CLOSE);
                        }
                    }
                }
                if ((viewQuickLinkref != null) && !viewQuickLinkref.getLink_type().equalsIgnoreCase(M)) {
                    html.append(IMenuDTO.MENU_UL_CLOSE);
                }

            }
            if ((viewQuickLinkref != null) && !viewQuickLinkref.getLink_type().equalsIgnoreCase(M)) {
                html.append(IMenuDTO.MENU_LI_CLOSE);
            }
        }

        return html.toString();
    }

    private String prepareMenuString(final List<VIEWQuickLink> listQuickLinks, final CsrfToken token, int languageId) {
        final StringBuffer html = new StringBuffer();
        VIEWQuickLink viewQuickLinkref = null;
        VIEWQuickLink viewQuickLink = null;

        if (listQuickLinks == null) {

            return html.toString();

        } else {

            if (listQuickLinks.size() > 0) {

                for (final ListIterator<VIEWQuickLink> iterator = listQuickLinks.listIterator(); iterator.hasNext();) {

                    viewQuickLink = iterator.next();
                    viewQuickLinkref = viewQuickLink;

                    if ((viewQuickLink.getChild() != null)
                            && (viewQuickLink.getChild().size() > 0)) {
                        if (viewQuickLink.getIsLinkModify().equals(MainetConstants.MENU.F)) {
                            html.append(IMenuDTO.MENU_LI_PARENT);
                        } else {
                            html.append(IMenuDTO.MENU_LI_PARENT_NEW);
                        }

                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                        if (languageId == MainetConstants.ENGLISH) {
                            html.append("title='" + viewQuickLink.getMenu_name_eng() + "'");
                        } else {
                            html.append("title='" + viewQuickLink.getMenu_name_reg() + "'");
                        }
                        html.append(IMenuDTO.CLOSE_TAG);

                        if (languageId == MainetConstants.ENGLISH) {
                            html.append(viewQuickLink.getMenu_name_eng()+ ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : ""));
                        } else {
                            html.append(viewQuickLink.getMenu_name_reg()+ ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : ""));
                        }

                        html.append(IMenuDTO.MENU_ANCHOR_CLOSE);

                        html.append(IMenuDTO.MENU_UL_PARENT);
                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                        html.append(IMenuDTO.CLOSE_TAG);
                        html.append(prepareMenuString(viewQuickLink.getChild(), token, languageId));
                    } else {

                        if (viewQuickLink != null) {

                            if (viewQuickLink.getIsLinkModify().equals(MainetConstants.MENU.F)) {
                                html.append(IMenuDTO.MENU_LI_OPEN);
                            } else {
                                html.append(IMenuDTO.MENU_LI_OPEN_NEW);
                            }

                            if ((viewQuickLink.getParent_id() != null)
                                    && (viewQuickLink.getLink_type() != null)
                                    && (viewQuickLink.getParent_id().indexOf(MainetConstants.MENU.F) != -1)
                                    && viewQuickLink.getLink_type().equalsIgnoreCase("f") && (viewQuickLink.getPage_url() != null)
                                    && viewQuickLink.getPage_url()
                                            .equalsIgnoreCase(MainetConstants.EIPSection.DEFAULT_PAGE_URL)) {
                            	 html.append("<li class='blink'><a href='SectionInformation.html?editForm&rowId="+ viewQuickLink.getLinkid() +"&page='");
                            	 if (languageId == MainetConstants.ENGLISH) {
								html.append("title='"+viewQuickLink.getMenu_name_eng()+"'>"+viewQuickLink.getMenu_name_eng()
								+ ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
								+"</a>");
								}else {
								html.append("title='"+viewQuickLink.getMenu_name_reg()+"'>"+viewQuickLink.getMenu_name_reg()
								+ ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
								+"</a></li>");
										}

                            	 
                            	
                               /* html.append("<form action='SectionInformation.html?editForm=" + viewQuickLink.getLinkid()
                                        + "' method='post' name='frm" + viewQuickLink.getLinkid() + "'");
                                html.append(" id='frm" + viewQuickLink.getLinkid() + "'>");
                                html.append("<input type='hidden' name='_csrf'  value='" + token.getToken() + "' />");

                                if (languageId == MainetConstants.ENGLISH) {
                                    if (viewQuickLink.getPage_url().split("://").length > 1) {
                                        html.append(
                                                "<li class='blink'><input type='submit'  title='"
                                                        + viewQuickLink.getMenu_name_eng() + "' value='"
                                                        + viewQuickLink.getMenu_name_eng() + "' /></li>");
                                    } else {
                                        html.append(
                                                "<li class='blink'><input type='submit'  title='"
                                                        + viewQuickLink.getMenu_name_eng() + "' value='"
                                                        + viewQuickLink.getMenu_name_eng() + "' /></li>");
                                    }
                                } else {
                                    if (viewQuickLink.getPage_url().split("://").length > 1) {
                                        html.append(
                                                "<li class='blink'><input type='submit'  title='"
                                                        + viewQuickLink.getMenu_name_reg() + "' value='"
                                                        + viewQuickLink.getMenu_name_reg() + "' /></li>");
                                    } else {
                                        html.append("<li class='blink'><input type='submit' title='"
                                                + viewQuickLink.getMenu_name_reg() + "' value='"
                                                + viewQuickLink.getMenu_name_reg() + "' /></li>");
                                    }
                                }

                                html.append("<input type=\"hidden\" name=\"rowId\" id=\"rowId" + viewQuickLink.getLinkid());
                                html.append("\" value='" + viewQuickLink.getLinkid() + "'/>");
                                html.append("</form>");*/
                            } else if ((viewQuickLink.getParent_id() != null)
                                    && (viewQuickLink.getLink_type() != null) && (viewQuickLink.getParent_id().indexOf("M") != -1)
                                    && viewQuickLink.getLink_type().equalsIgnoreCase(MainetConstants.MENU.F)) {

                                if ((viewQuickLink.getHaSubLink() != null) && viewQuickLink.getHaSubLink().equalsIgnoreCase("N")
                                        && (viewQuickLink.getPage_url() != null) && viewQuickLink.getPage_url()
                                                .equalsIgnoreCase(MainetConstants.EIPSection.DEFAULT_PAGE_URL)) {
                                	
                                	 html.append("<a href='SectionInformation.html?editForm&rowId="+ viewQuickLink.getLinkid() +"&page='");
                                	 if (languageId == MainetConstants.ENGLISH) {
    								html.append("title='"+viewQuickLink.getMenu_name_eng()+"'>"+viewQuickLink.getMenu_name_eng()
    								+ ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
    								+"</a>");
    								}else {
    								html.append("title='"+viewQuickLink.getMenu_name_reg()+"'>"+viewQuickLink.getMenu_name_reg()
    								+ ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
    								+"</a>");
    								}
    								/*
                                    html.append("<form action='SectionInformation.html?editForm=" + viewQuickLink.getLinkid()
                                            + "' method='post' name='frm" + viewQuickLink.getLinkid()
                                            + MainetConstants.operator.DOUBLE_QUOTES);
                                    html.append(" id='frm" + viewQuickLink.getLinkid() + "'>");
                                    html.append("<input type='hidden' name='_csrf'  value='" + token.getToken() + "' />");
                                    if (languageId == MainetConstants.ENGLISH) {
                                        if (viewQuickLink.getPage_url().split("://").length > 1) {
                                            html.append("<li class='input-link'><input type='submit'  title="
                                                    + viewQuickLink.getMenu_name_eng() + "' value='"
                                                    + viewQuickLink.getMenu_name_eng() + "' /></li>");
                                        } else {
                                            html.append("<li class='input-link'><input type='submit' title='"
                                                    + viewQuickLink.getMenu_name_eng() + "' value='"
                                                    + viewQuickLink.getMenu_name_eng() + "' /></li>");
                                        }
                                    } else {
                                        if (viewQuickLink.getPage_url().split("://").length > 1) {
                                            html.append("<li class='input-link'><input type='submit' title='"
                                                    + viewQuickLink.getMenu_name_reg() + "' value='"
                                                    + viewQuickLink.getMenu_name_reg() + "' /></li>");
                                        } else {
                                            html.append("<li class='input-link'><input type='submit' title='"
                                                    + viewQuickLink.getMenu_name_reg() + "' value='"
                                                    + viewQuickLink.getMenu_name_reg() + "' /></li>");
                                        }
                                    }

                                    html.append("<input type=\"hidden\" name=\"rowId\" id=\"rowId" + viewQuickLink.getLinkid());
                                    html.append("\" value='" + viewQuickLink.getLinkid() + "'/>");
                                    html.append("</form>");*/
                                } else {
                                    html.append(IMenuDTO.MENU_ANCHOR_OPEN);
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append(viewQuickLink.getPage_url());
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                    if (languageId == MainetConstants.ENGLISH) {
                                        html.append(IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_eng()
                                        + ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
                                                + IMenuDTO.MENU_ANCHOR_CLOSE);
                                    } else {
                                        html.append(IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_reg()
                                        + ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
                                                + IMenuDTO.MENU_ANCHOR_CLOSE);
                                    }
                                }
                            }

                            else if ((viewQuickLink.getParent_id() != null)
                                    && (viewQuickLink.getLink_type() != null)
                                    && viewQuickLink.getParent_id().equalsIgnoreCase("M")
                                    && viewQuickLink.getLink_type().equalsIgnoreCase("M")) {

                                if ((viewQuickLink.getPage_url().split("://").length > 1)
                                        || viewQuickLink.getMenu_name_eng().contains("Smart City")) {
                                	html.append(IMenuDTO.MENU_ANCHOR_OPEN);
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append(viewQuickLink.getPage_url());
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                    html.append("target='_blank'");
                                    /*html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append("showConfirm('" + viewQuickLink.getPage_url() + "');");
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                    html.append("target='_blank'");*/
                                } else {
                                    html.append(IMenuDTO.MENU_ANCHOR_OPEN);
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                                    html.append(viewQuickLink.getPage_url());
                                    html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + MainetConstants.WHITE_SPACE);
                                }

                                if (languageId == MainetConstants.ENGLISH) {
                                    html.append("class='extlink'");
                                    html.append(
                                            IMenuDTO.CLOSE_TAG + viewQuickLink.getMenu_name_eng() 
                                            + ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
                                            + IMenuDTO.MENU_ANCHOR_CLOSE);
                                } else {
                                    html.append("class='extlink'");
                                    html.append(
                                            IMenuDTO.CLOSE_TAG +viewQuickLink.getMenu_name_reg() 
                                            + ( getFlashCheck(viewQuickLink.getHaSubLink() ,viewQuickLink.getIsLinkModify()) ? IMenuDTO.FLASHIMAGE : "")
                                            + IMenuDTO.MENU_ANCHOR_CLOSE);
                                }

                            }
                            html.append(IMenuDTO.MENU_LI_CLOSE);
                        }
                    }
                }
                if ((viewQuickLinkref != null) && !viewQuickLinkref.getLink_type().equalsIgnoreCase(M)) {
                    html.append(IMenuDTO.MENU_UL_CLOSE);
                }

            }
            if ((viewQuickLinkref != null) && !viewQuickLinkref.getLink_type().equalsIgnoreCase(M)) {
                html.append(IMenuDTO.MENU_LI_CLOSE);
            }
        }

        return html.toString();
    }

    public String getQuickLinkMenu() {
        return quickLinkMenu;
    }
    
    private boolean getFlashCheck(String subLink,String isModify) {
    	if(isModify != null && "T".equalsIgnoreCase(isModify)) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    @Override
    @Transactional(readOnly = true)
    public VIEWQuickLinkDTO getQuickLinkListData(final Organisation org) {

        quickLinkMenu = new String();

        List<VIEWQuickLink> viewQuickLinkList = null;

        if (org != null) {
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.APP_NAME.SUDA)) {
				viewQuickLinkList = getQuickLinkListSuda(org);
			} else {
				viewQuickLinkList = getQuickLinkList(org);
			}
		}

        if (viewQuickLinkList == null) {
            viewQuickLinkList = new ArrayList<>();
        }
        final List<VIEWQuickLink> listContainerQuickLinks = prepareParentMenuList(viewQuickLinkList);
        Optional<VIEWQuickLink> viewQuickLink = listContainerQuickLinks.stream().filter(listData -> listData.getMenu_name_eng()!=null && listData.getMenu_name_eng().equals(MainetConstants.About_KDMC)).findFirst();
        VIEWQuickLinkDTO viewQuickLinkDto = new VIEWQuickLinkDTO();
        if(viewQuickLink!=null && viewQuickLink.isPresent()) {       	
            BeanUtils.copyProperties(viewQuickLink, viewQuickLinkDto);
            ArrayList<VIEWQuickLinkDTO> List = new ArrayList<>();
            for(VIEWQuickLink viewList : viewQuickLink.get().getChild()){
            	VIEWQuickLinkDTO viewQuickLinkDtoChild = new VIEWQuickLinkDTO();				
    			Hibernate.initialize(viewList.getChild());
    			viewQuickLinkDtoChild = viewQuickLinkMapper.mapEntityToDTO(viewList);        	
            	ArrayList<VIEWQuickLinkDTO> setquickLinkList = setquickLinkList(viewQuickLinkDtoChild);
            	viewQuickLinkDtoChild.setChildList(setquickLinkList);
            	List.add(viewQuickLinkDtoChild);
            }
            viewQuickLinkDto.setChildList(List);
            viewQuickLinkDto.setParent(null);
        }
        
        return viewQuickLinkDto;
    }
    
    public ArrayList<VIEWQuickLinkDTO> setquickLinkList(VIEWQuickLinkDTO viewQuickLinkDtoChild2){
    	ArrayList<VIEWQuickLinkDTO> List2 = new ArrayList<>();
    	ArrayList<VIEWQuickLinkDTO> childList = null;
    	if(viewQuickLinkDtoChild2.getChild()!= null && !viewQuickLinkDtoChild2.getChild().isEmpty()){
	        for(VIEWQuickLink viewChildList : viewQuickLinkDtoChild2.getChild()){
	        	VIEWQuickLinkDTO viewQuickLinkDtoChildList = new VIEWQuickLinkDTO();
				Hibernate.initialize(viewChildList.getChild());
				viewQuickLinkDtoChildList = viewQuickLinkMapper.mapEntityToDTO(viewChildList);
				if(viewQuickLinkDtoChildList.getChild()!=null && !viewQuickLinkDtoChildList.getChild().isEmpty()){
		        	ArrayList<VIEWQuickLinkDTO> setquickLinkList = setquickLinkList(viewQuickLinkDtoChildList);
		        	viewQuickLinkDtoChildList.setChildList(setquickLinkList);
				}
				List2.add(viewQuickLinkDtoChildList);
	        }
	        childList=List2;
    	}
		return childList;
    }
    

}
