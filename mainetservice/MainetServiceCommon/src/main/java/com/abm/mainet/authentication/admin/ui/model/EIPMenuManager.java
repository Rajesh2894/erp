package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.entitlement.dto.IMenuDTO;
import com.abm.mainet.common.entitlement.dto.MenuManager;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.FavouriteMenuAsJson;
import com.abm.mainet.common.utility.HttpHelper;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author pabitra.raulo
 */
@Component("eipMenuManager")
@Scope(value = "session")
public class EIPMenuManager extends MenuManager {
    private static final long serialVersionUID = -5357371466725425334L;

    private List<MenuDTO> listSystems = new ArrayList<>(0);
    private List<MenuDTO> listModules = new ArrayList<>(0);
    private Map<Long, String> smfActionMap = new HashMap<>(0);
    private final Map<Long, String> formNameMap = new HashMap<>(0);
    private Map<Long, Long> smfIdDeptMap = new HashMap<>(0);
    private Map<Long, Long> smfIdServiceMap = new HashMap<>(0);
    private Map<Long, String> addFaouritMapper = new HashMap<>(0);
    private List<FavouriteMenuAsJson> favMenuList = new ArrayList<>(0);
    private Map<Long, String> smfidMenuparam1 = new HashMap<>(0);
    private Map<Long, String> smfidMenuparam2 = new HashMap<>(0);
    private Map<Long, String> mapBreadCrumb;
    private List<MenuDTO> menuGroupWise = new ArrayList<>(0);
    private String serviceType;
    private String deptFlag;
    private String locationFlag;
    private StringBuffer favoriteMenu;
    private List<MenuDTO> menuLister = new ArrayList<>(0);
    private long maxSize;
    private boolean citizenType;
    private boolean agencyType;
    private String userType;
    private String onlineCznServiceFlag = "false";
    private String loggedInMessage;
    private String citizenMenuString;
    private String onliserviceMenu;
    private String d2kURL;
    private String userIp;
    private String menuString;
    private String moduleType;

    public EIPMenuManager() {
        setModuleName("EIP");
    }

    public EIPMenuManager(final String moduleName, final String moduleTupe) {
        super(moduleName);
        moduleType = "WEB-Portal";
    }

    private class MenuDTO implements Comparable<Object>, Serializable {
        private static final long serialVersionUID = 1L;
        private long mntId;
        private Long parenId;
        private String menuname;
        private String menuType;
        private boolean rPermission;
        private boolean wPermission;
        private MenuDTO parentMenu;
        private String menuName_MR;
        private List<MenuDTO> childMenu;
        private String smfaction;
        private Long deptId;
        private Long serviceId;
        private String menuParam1;
        private String menuParam2;
        private String smfflag;
        private String smfcategory;
        private long depth;
        private String deptCategory;
        private Double smfsrno;

        MenuDTO(final int mntId) {
            this.mntId = mntId;
        }

        public List<MenuDTO> getChildMenu() {
            return childMenu;
        }

        public long getMntId() {
            return mntId;
        }

        public Long getParenId() {
            return parenId;
        }

        public String getMenuname() {
            return menuname;
        }

        public void setMenuname(final String menuname) {
            this.menuname = menuname;
        }

        public String getMenuType() {
            return menuType;
        }

        public void setMenuType(final String menuType) {
            this.menuType = menuType;
        }

        public String getMenuName_MR() {
            return menuName_MR;
        }

        public String getSmfaction() {
            return smfaction;
        }

        public Long getDeptId() {
            return deptId;
        }

        public Long getServiceId() {
            return serviceId;
        }

        public String getMenuParam2() {
            return menuParam2;
        }

        public String getMenuParam1() {
            return menuParam1;
        }

        public String getSmfcategory() {
            return smfcategory;
        }

        public String getSmfflag() {
            return smfflag;
        }

        @Override
        public int compareTo(final Object obj) {
            if (obj instanceof MenuDTO) {
                if (menuname.equalsIgnoreCase(((MenuDTO) obj).getMenuname())) {
                    return 0;
                } else if (menuname.compareTo(((MenuDTO) obj).getMenuname()) < 0) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }

        }

        public long getDepth() {
            return depth;
        }

        public String getDeptCategory() {
            return deptCategory;
        }

        public void setDeptCategory(final String deptCategory) {
            this.deptCategory = deptCategory;
        }

        public Double getSmfsrno() {
            return smfsrno;
        }

    }

    private class MenuDTOSmfSrnSort implements Comparator<MenuDTO> {

        @Override
        public int compare(final MenuDTO mnt1, final MenuDTO mnt2) {
            return mnt1.getSmfsrno().compareTo(mnt2.getSmfsrno());
        }
    } // Sorting By SMF Serial No

    private class MenuDTONameSort implements Comparator<MenuDTO> {

        @Override
        public int compare(final MenuDTO mnt1, final MenuDTO mnt2) {
            return mnt1.getMenuname().compareTo(mnt2.getMenuname());
        }
    } // Sorting By SMF Serial No

    @Override
    public String getServiceType() {
        return serviceType;
    }

    @Override
    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDeptFlag() {
        return deptFlag;
    }

    public void setDeptFlag(final String deptFlag) {
        this.deptFlag = deptFlag;
    }

    public String getLocationFlag() {
        return locationFlag;
    }

    public void setLocationFlag(final String locationFlag) {
        this.locationFlag = locationFlag;
    }

    public StringBuffer getFavoriteMenu() {
        return favoriteMenu;
    }

    public void setFavoriteMenu(final StringBuffer favoriteMenu) {
        this.favoriteMenu = favoriteMenu;
    }

    public List<MenuDTO> getMenuLister() {
        return menuLister;
    }

    public void setMenuLister(final List<MenuDTO> menuLister) {
        this.menuLister = menuLister;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(final long maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isCitizenType() {
        return citizenType;
    }

    public void setCitizenType(final boolean citizenType) {
        this.citizenType = citizenType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    public String getOnlineCznServiceFlag() {
        return onlineCznServiceFlag;
    }

    public void setOnlineCznServiceFlag(final String onlineCznServiceFlag) {
        this.onlineCznServiceFlag = onlineCznServiceFlag;
    }

    public String getLoggedInMessage() {
        return loggedInMessage;
    }

    public void setLoggedInMessage(final String loggedInMessage) {
        this.loggedInMessage = loggedInMessage;
    }

    public String getCitizenMenuString() {

        return citizenMenuString;
    }

    public void setCitizenMenuString(final String citizenMenuString) {
        if (isCitizenType()) {
            super.setOnLineCznMenuHtml(citizenMenuString);
        } else if ((UserSession.getCurrent().getEmployee().getEmploginname() != null)
                && UserSession.getCurrent().getEmployee().getEmploginname().equalsIgnoreCase("nouser")) {
            super.setNoONLCznMenuHtml(citizenMenuString);
        }
        this.citizenMenuString = citizenMenuString;
    }

    public String getOnliserviceMenu() {
        return onliserviceMenu;
    }

    public void setOnliserviceMenu(final String onliserviceMenu) {
        this.onliserviceMenu = onliserviceMenu;
    }

    public String getD2kURL() {
        return d2kURL;
    }

    public void setD2kURL(final String d2kURL) {
        this.d2kURL = d2kURL;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(final String userIp) {
        this.userIp = userIp;
    }

    public String getMenuString() {
        return menuString;
    }

    public void setMenuString(final String menuString) {
        super.setDeptMenuHTML(menuString);
        this.menuString = menuString;
    }

    public void arrangeMenus(final long userId) {
        addFaouritMapper = new HashMap<>(0);
        smfActionMap = new HashMap<>(0);
        smfIdDeptMap = new HashMap<>(0);
        smfIdServiceMap = new HashMap<>(0);
        smfidMenuparam1 = new HashMap<>(0);
        smfidMenuparam2 = new HashMap<>(0);

        listSystems = new ArrayList<>(0);
        listModules = new ArrayList<>(0);
        menuLister = new ArrayList<>(0);

        menuString = null;
        citizenMenuString = null;

        try {
            final UserSession current_User = UserSession.getCurrent();

            final Employee employee = current_User.getEmployee();

            final Organisation organisation = current_User.getOrganisation();

            final Map<Long, MenuDTO> menuDtosMap = new HashMap<>();

            if ((userId > 0l) && (employee != null) && (organisation != null)) {
                if (isCitizenType()) {
                } else {
                }
            }

            for (final MenuDTO item : menuDtosMap.values()) {
                if (item.childMenu != null) {
                    Collections.sort(item.childMenu, new MenuDTOSmfSrnSort());
                }
            }

            setMaxSize(menuDtosMap.size());

            /**
             * START ACCORDIAN LIST 10-JAN-2014
             */

            final ListIterator<MenuDTO> gruopWiseItr = menuGroupWise.listIterator();
            while (gruopWiseItr.hasNext()) {
                gruopWiseItr.next();
            }

            /** END OF ACCORIAN LIST 10-JAN-2014 */

            /*************************
             * @author pabitra.raulo
             * @since 10-JAN-2014 Here the Accordian Menu for user MENU SETUP Done
             *************************/

            java.util.Collections.sort(listModules, new MenuDTOSmfSrnSort());
            java.util.Collections.sort(listSystems, new MenuDTONameSort());

            if ((employee != null) && !employee.getEmploginname().equalsIgnoreCase("nouser") && !isCitizenType()) {
                /** * GroupWise Menu */
                final StringBuilder html = new StringBuilder();
                getGroupingMenu(html, listSystems);
                setMenuString(html.toString());
            } else if (isCitizenType()) {
                final StringBuilder html = new StringBuilder();
                arrangeRecursive_Menu(html, getListModules());
                setCitizenMenuString(html.toString());
            }
        } catch (final Exception e) {
        } finally {

        }
    }

    public Map<Long, String> getSmfActionMap() {
        return smfActionMap;
    }

    public void setSmfActionMap(final Map<Long, String> smfActionMap) {
        this.smfActionMap = smfActionMap;
    }

    public Map<Long, Long> getSmfIdDeptMap() {
        return smfIdDeptMap;
    }

    public void setSmfIdDeptMap(final Map<Long, Long> smfIdDeptMap) {
        this.smfIdDeptMap = smfIdDeptMap;
    }

    public Map<Long, Long> getSmfIdServiceMap() {
        return smfIdServiceMap;
    }

    public void setSmfIdServiceMap(final Map<Long, Long> smfIdServiceMap) {
        this.smfIdServiceMap = smfIdServiceMap;
    }

    public Map<Long, String> getAddFaouritMapper() {
        return addFaouritMapper;
    }

    public void setAddFaouritMapper(final Map<Long, String> addFaouritMapper) {
        this.addFaouritMapper = addFaouritMapper;
    }

    public List<FavouriteMenuAsJson> getFavMenuList() {
        return favMenuList;
    }

    public void setFavMenuList(final List<FavouriteMenuAsJson> favMenuList) {
        this.favMenuList = favMenuList;
    }

    public Map<Long, String> getSmfidMenuparam1() {
        return smfidMenuparam1;
    }

    public void setSmfidMenuparam1(final Map<Long, String> smfidMenuparam1) {
        this.smfidMenuparam1 = smfidMenuparam1;
    }

    public Map<Long, String> getSmfidMenuparam2() {
        return smfidMenuparam2;
    }

    public void setSmfidMenuparam2(final Map<Long, String> smfidMenuparam2) {
        this.smfidMenuparam2 = smfidMenuparam2;
    }

    public Map<Long, String> getMapBreadCrumb() {
        return mapBreadCrumb;
    }

    public void setMapBreadCrumb(final Map<Long, String> mapBreadCrumb) {
        this.mapBreadCrumb = mapBreadCrumb;
    }

    public List<MenuDTO> getMenuGroupWise() {
        return menuGroupWise;
    }

    public void setMenuGroupWise(final List<MenuDTO> menuGroupWise) {
        this.menuGroupWise = menuGroupWise;
    }

    /**
     * @author pabitra.raulo
     * @param menuList
     * @return Menu String From The Module Label i.e Module->Function
     */
    private void arrangeRecursive_Menu(final StringBuilder html, final Collection<MenuDTO> menuList) {
        String favHTML = "";
        MenuDTO menuDto = null;

        if (menuList == null) {
            return;
        } else {
            if (menuList.size() > 0) {
                for (final MenuDTO menuDTO : menuList) {
                    menuDto = menuDTO;

                    if ((menuDTO.getChildMenu() != null) && (menuDTO.getChildMenu().size() > 0)) {
                        html.append(IMenuDTO.MENU_LI_PARENT).append(menuDTO.getMntId()).append(IMenuDTO.ESCAPE_DOUBLE_COUTTE)
                                .append(IMenuDTO.CLOSE_TAG).append(menuDTO.getMenuname()).append(IMenuDTO.MENU_ANCHOR_CLOSE);
                        html.append(IMenuDTO.MENU_UL_PARENT).append(menuDTO.getMntId()).append(IMenuDTO.ESCAPE_DOUBLE_COUTTE)
                                .append(IMenuDTO.CLOSE_TAG);
                        arrangeRecursive_Menu(html, menuDTO.getChildMenu());
                    } else {
                        final UserSession userSession = UserSession.getCurrent();
                        if ((menuDTO != null) && (menuDTO.getSmfaction() != "")) {
                            if (menuDTO.getMenuType().equalsIgnoreCase("F")) {
                                if (userSession.getEmployee().getEmpId() > 0) { // To avoid Add Favorite
                                    html.append(IMenuDTO.OPEN_TAG).append(IMenuDTO.ADD_FAVOURITE_DIV_CLASS)
                                            .append(" onclick =\"addtoFavourite(\'").append(menuDTO.getMntId()).append("\',\'");

                                    if (userSession.getLanguageId() == 1) {
                                        if (menuDTO.getMenuname() != null) {
                                            html.append(menuDTO.getMenuname().trim());
                                        }
                                    } else {
                                        if (menuDTO.getMenuName_MR() != null) {
                                            html.append(menuDTO.getMenuName_MR().trim());
                                        }
                                    } // Check The ManuLable By LangId

                                    html.append("\')\" ").append(IMenuDTO.CLOSE_TAG).append(IMenuDTO.ADD_FAVOURITE_DIV_IMG)
                                            .append(IMenuDTO.MENU_ANCHOR_CLOSE + IMenuDTO.MENU_DIV_CLOSE);
                                }
                            } // To Show Add FAVORITES ICON

                            html.append(IMenuDTO.MENU_LI_OPEN).append(IMenuDTO.MENU_ANCHOR_OPEN)
                                    .append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                            favHTML = IMenuDTO.MENU_LI_OPEN + IMenuDTO.MENU_ANCHOR_OPEN + IMenuDTO.ESCAPE_DOUBLE_COUTTE;
                            final ApplicationSession appsession = ApplicationSession.getInstance();
                            String d2kServerName = HttpHelper
                                    .getDomainURL(String.valueOf(userSession.getOrganisation().getOrgid()), true);
                            if ((null == d2kServerName) || d2kServerName.isEmpty()) {
                                d2kServerName = appsession.getMessage("HTTP_Protocol") + "://" + appsession.getMessage("ip") + ":"
                                        + appsession.getMessage("port");
                            } else {
                                d2kServerName = d2kServerName.substring(0, d2kServerName.length() - 1);
                                d2kServerName = d2kServerName.substring(0, d2kServerName.lastIndexOf('/'));
                            }
                            final String d2kURL = d2kServerName + appsession.getMessage("D2KContext") + "?"
                                    + appsession.getMessage("param1") + "=" + appsession.getMessage("param1.value") + "&"
                                    + appsession.getMessage("param4") + "=";
                            smfActionMap.put(Long.valueOf(menuDTO.getMntId()).longValue(), d2kURL);
                            smfIdDeptMap.put(menuDTO.getMntId(), menuDTO.getDeptId());
                            smfIdServiceMap.put(menuDTO.getMntId(), menuDTO.getServiceId());
                            smfidMenuparam1.put(menuDTO.getMntId(), menuDTO.getMenuParam1());
                            smfidMenuparam2.put(menuDTO.getMntId(), menuDTO.getMenuParam2());
                            formNameMap.put(Long.valueOf(menuDTO.getMntId()).longValue(),
                                    menuDTO.getSmfaction() != null ? menuDTO.getSmfaction().toUpperCase() + ".fmx" : "");

                            if ((menuDTO.getSmfcategory() != null) && menuDTO.getSmfcategory().equalsIgnoreCase("U")) {
                                html.append(menuDTO.getSmfaction());
                                favHTML += menuDTO.getSmfaction();
                            } else if ((menuDTO.getSmfcategory() != null) && (menuDTO.getSmfcategory().equalsIgnoreCase("F")
                                    || menuDTO.getSmfcategory().equalsIgnoreCase("S"))) {
                                html.append("#");
                                favHTML += "#";
                            } else {
                                html.append("#");
                            }
                        }
                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE + " ");
                        favHTML += IMenuDTO.ESCAPE_DOUBLE_COUTTE + " ";
                        if ((menuDTO != null) && (menuDTO.getSmfaction() != "") && menuDto.getMenuType().equals("F")
                                && (menuDTO.getSmfcategory() != null) && (menuDTO.getSmfcategory() != null)) {
                            if (menuDTO.getSmfcategory().equalsIgnoreCase("f")
                                    || menuDTO.getSmfcategory().equalsIgnoreCase("s")) {
                                html.append(IMenuDTO.MENU_ANCHOR_D2K_ONCLICK).append("\'").append(menuDto.mntId).append("\'")
                                        .append(IMenuDTO.MENU_ANCHOR_D2K_ONCLICKCLS);
                                favHTML += IMenuDTO.MENU_ANCHOR_D2K_ONCLICK + "\'" + menuDto.mntId + "\'"
                                        + IMenuDTO.MENU_ANCHOR_D2K_ONCLICKCLS;
                            }
                        }
                        html.append(IMenuDTO.CLOSE_TAG).append(menuDTO.getMenuname()).append(IMenuDTO.MENU_ANCHOR_CLOSE);
                        html.append(IMenuDTO.MENU_LI_CLOSE);
                        favHTML += IMenuDTO.CLOSE_TAG + menuDTO.getMenuname() + IMenuDTO.MENU_ANCHOR_CLOSE
                                + IMenuDTO.MENU_LI_CLOSE;
                        addFaouritMapper.put(menuDTO.getMntId(), favHTML);
                    }

                }
                if (((menuDto != null) && menuDto.getMenuType().equalsIgnoreCase("F"))
                        || (menuDto.getMenuType().equalsIgnoreCase("m") && (menuDto.getDepth() >= 3l))) {
                    html.append(IMenuDTO.MENU_UL_CLOSE);
                }
            }
            if (((menuDto != null) && menuDto.getMenuType().equalsIgnoreCase("F"))
                    || (menuDto.getMenuType().equalsIgnoreCase("m") && (menuDto.getDepth() >= 3l))) {
                html.append(IMenuDTO.MENU_LI_CLOSE);
            }
            return;
        }

    }

    private void getGroupingMenu(final StringBuilder html, final Collection<MenuDTO> menuList) {
        String favHTML = "";
        MenuDTO menuDto = null;
        MenuDTO menuDTO = null;

        if (menuList == null) {
            return;
        } else {
            if (menuList.size() > 0) {
                final ListIterator<MenuDTO> iterator = ((ArrayList<MenuDTO>) menuList).listIterator();
                final UserSession userSession = UserSession.getCurrent();

                while (iterator.hasNext()) {

                    menuDTO = iterator.next();

                    menuDto = menuDTO;

                    if ((menuDTO.getChildMenu() != null) && (menuDTO.getChildMenu().size() > 0)) {
                        html.append(IMenuDTO.MENU_LI_PARENT).append(menuDTO.getMntId()).append(IMenuDTO.ESCAPE_DOUBLE_COUTTE)
                                .append(IMenuDTO.CLOSE_TAG).append(menuDTO.getMenuname()).append(IMenuDTO.MENU_ANCHOR_CLOSE);
                        html.append(IMenuDTO.MENU_UL_PARENT).append(menuDTO.getMntId()).append(IMenuDTO.ESCAPE_DOUBLE_COUTTE)
                                .append(IMenuDTO.CLOSE_TAG);
                        getGroupingMenu(html, menuDTO.getChildMenu());
                        if (menuDTO.getMenuType().equalsIgnoreCase("s")) {
                            setServiceType(menuDTO.getMenuname());
                        }
                    } else {
                        if ((menuDTO != null) && (menuDTO.getSmfaction() != "")) {
                            if (menuDTO.getMenuType().equalsIgnoreCase("F")) {
                                html.append(IMenuDTO.OPEN_TAG).append(IMenuDTO.ADD_FAVOURITE_DIV_CLASS)
                                        .append(" onclick = \"addtoFavourite(\'").append(menuDTO.getMntId()).append("\',\'");

                                if (userSession.getLanguageId() == 1)// ENGLISH
                                {
                                    if (menuDTO.getMenuname() != null) {
                                        html.append(menuDTO.getMenuname().trim());
                                    }
                                } else if (userSession.getLanguageId() == 2) // HINDI
                                {
                                    if (menuDTO.getMenuName_MR() != null) {
                                        html.append(menuDTO.getMenuName_MR().trim());
                                    }
                                } // Check The ManuLable By LangId

                                html.append("\')\" ").append(IMenuDTO.CLOSE_TAG).append(IMenuDTO.ADD_FAVOURITE_DIV_IMG)
                                        .append(IMenuDTO.MENU_ANCHOR_CLOSE).append(IMenuDTO.MENU_DIV_CLOSE);
                            }

                            html.append(IMenuDTO.MENU_LI_OPEN).append(IMenuDTO.MENU_ANCHOR_OPEN)
                                    .append(IMenuDTO.ESCAPE_DOUBLE_COUTTE);
                            favHTML = IMenuDTO.MENU_LI_OPEN + IMenuDTO.MENU_ANCHOR_OPEN + IMenuDTO.ESCAPE_DOUBLE_COUTTE;

                            final ApplicationSession appsession = ApplicationSession.getInstance();

                            String d2kServerName = HttpHelper
                                    .getDomainURL(String.valueOf(userSession.getOrganisation().getOrgid()), true);
                            if ((null == d2kServerName) || d2kServerName.isEmpty()) {
                                d2kServerName = appsession.getMessage("HTTP_Protocol") + "://" + appsession.getMessage("ip") + ":"
                                        + appsession.getMessage("port");
                            } else {
                                d2kServerName = d2kServerName.substring(0, d2kServerName.length() - 1);
                                d2kServerName = d2kServerName.substring(0, d2kServerName.lastIndexOf('/'));
                            }
                            final String d2kURL = d2kServerName + appsession.getMessage("D2KContext") + "?"
                                    + appsession.getMessage("param1") + "=" + appsession.getMessage("param1.value") + "&"
                                    + appsession.getMessage("param4") + "=";

                            smfActionMap.put(Long.valueOf(menuDTO.getMntId()).longValue(), d2kURL);
                            formNameMap.put(Long.valueOf(menuDTO.getMntId()).longValue(),
                                    menuDTO.getSmfaction() != null ? menuDTO.getSmfaction().toUpperCase() + ".fmx" : "");
                            smfIdDeptMap.put(menuDTO.getMntId(), menuDTO.getDeptId());
                            smfIdServiceMap.put(menuDTO.getMntId(), menuDTO.getServiceId());
                            smfidMenuparam1.put(menuDTO.getMntId(), menuDTO.getMenuParam1());
                            smfidMenuparam2.put(menuDTO.getMntId(), menuDTO.getMenuParam2());

                            if ((menuDTO.getMenuType() != null) && menuDTO.getMenuType().equalsIgnoreCase("S")
                                    && (menuDTO.getSmfaction() != "")) {
                                html.append(menuDTO.getSmfaction() + userSession.getEmployee().getEmpisecuritykey());
                                favHTML += menuDTO.getSmfaction() + userSession.getEmployee().getEmpisecuritykey();
                            } else if ((menuDTO.getSmfcategory() != null) && menuDTO.getSmfcategory().equalsIgnoreCase("U")) {
                                html.append(menuDTO.getSmfaction());
                                favHTML += menuDTO.getSmfaction();
                            }

                            else if ((menuDTO.getSmfcategory() != null) && menuDTO.getSmfcategory().equalsIgnoreCase("F")) {
                                html.append("#");
                                favHTML += menuDTO.getSmfaction();
                            } else {
                                html.append("#");
                            }

                        }

                        html.append(IMenuDTO.ESCAPE_DOUBLE_COUTTE).append(" ");
                        favHTML += IMenuDTO.ESCAPE_DOUBLE_COUTTE + " ";
                        if ((menuDTO != null) && (menuDTO.getSmfaction() != "") && menuDTO.getMenuType().equalsIgnoreCase("f")
                                && (menuDTO.getSmfcategory() != null) && (menuDTO.getSmfcategory().equalsIgnoreCase("f")
                                        || menuDTO.getSmfcategory().equalsIgnoreCase("s"))) {

                            html.append(IMenuDTO.MENU_ANCHOR_D2K_ONCLICK).append("\'").append(menuDto.mntId).append("\'")
                                    .append(IMenuDTO.MENU_ANCHOR_D2K_ONCLICKCLS);
                            favHTML += IMenuDTO.MENU_ANCHOR_D2K_ONCLICK + "\'" + menuDto.mntId + "\'"
                                    + IMenuDTO.MENU_ANCHOR_D2K_ONCLICKCLS;
                        }

                        html.append(IMenuDTO.CLOSE_TAG).append(menuDTO.getMenuname()).append(IMenuDTO.MENU_ANCHOR_CLOSE);
                        favHTML += IMenuDTO.CLOSE_TAG + menuDTO.getMenuname() + IMenuDTO.MENU_ANCHOR_CLOSE;

                        html.append(IMenuDTO.MENU_LI_CLOSE);
                        favHTML += IMenuDTO.MENU_LI_CLOSE;

                        addFaouritMapper.put(menuDTO.getMntId(), favHTML);
                    }
                }

                if ((menuDto != null)
                        && (menuDto.getMenuType().equalsIgnoreCase("F") || menuDto.getMenuType().equalsIgnoreCase("m"))) {
                    html.append(IMenuDTO.MENU_UL_CLOSE);
                }

            }

            if ((menuDto != null)
                    && (menuDto.getMenuType().equalsIgnoreCase("F") || menuDto.getMenuType().equalsIgnoreCase("m"))) {
                html.append("</li>");
            }
            return;
        }
    }

    public List<MenuDTO> getListSystems() {
        return listSystems;
    }

    public void setListSystems(final List<MenuDTO> listSystems) {
        this.listSystems = listSystems;
    }

    public List<MenuDTO> getListModules() {
        return listModules;
    }

    public void setListModules(final List<MenuDTO> listModules) {
        this.listModules = listModules;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(final String moduleType) {
        this.moduleType = moduleType;
    }

    public boolean isAgencyType() {
        return agencyType;
    }

    public void setAgencyType(final boolean agencyType) {
        this.agencyType = agencyType;
    }

    public String getLoginType() {
        String returnVal = StringUtils.EMPTY;

        if (isCitizenType()) {
            returnVal = "C";
        }
        if (isAgencyType()) {
            returnVal = "A";
        }

        if (UserSession.getCurrent().getEmployee().getEmplType() == null) {
            returnVal = "D";
        }

        return returnVal;

    }
}
