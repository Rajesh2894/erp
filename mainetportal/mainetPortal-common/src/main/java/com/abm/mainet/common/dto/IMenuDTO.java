package com.abm.mainet.common.dto;

import org.springframework.stereotype.Component;

/**
 * @author pabitra.raulo
 */
@Component
public interface IMenuDTO<T> {

    IMenuDTO<T> getMenu();

    public String MENU_UL = "<ul></ul>";
    public String MENU_LI = "<li></li>";
    public String MENU_DIV = "<div></div>";
    public String MENU_UL_OPEN = "<ul>";
    public String MENU_UL_CLOSE = "</ul>";
    public String MENU_LI_OPEN = "<li>";
    public String MENU_LI_OPEN_NEW = "<li class=\"blink\">";
    public String MENU_LI_CLOSE = "</li>";
    public String MENU_LI_OPEN_SUBCLASS = "<li class=\"subChildMenu\">";
    public String MENU_LI_OPEN_MAINCLASS = "<li class=\"mainMenu\">";
    public String OPEN_TAG = "<";
    public String CLOSE_TAG = ">";
    public String SELF_CLOSE_TAG = "\">";
    public String MENU_LI_PARENT = "<li class=\"parent\"><a href =\"javascript:void(0);\"";
    public String MENU_LI_PARENT_NEW = "<li class=\"parent\"><a href =\"javascript:void(0);\" class=\"blink";
    public String MENU_LI_CHILD = "<li><a href =\"javascript:void(0);\">";
    public String MENU_UL_PARENT = "<ul class=\"";

    @Deprecated
    public String MENU_UL_CHILD = "";
    public String MENU_ANCHOR_OPEN = "<a href =";
    public String MENU_DEFAULT_LINK = "javascript:void(0);";

    public String MENU_ANCHOR_ATTR_RELATIVE = "";
    public String MENU_ANCHOR_D2K_ONCLICK = "onclick=\" openDialogD2K(";
    public String MENU_ANCHOR_D2K_ONCLICKCLS = "); \" ";
    public String MENU_ANCHOR_CLOSE = "</a>";
    public String ESCAPE_DOUBLE_COUTTE = "\"";
    public String MENU_DIV_OPEN = "<div>";
    public String MENU_DIV_CLOSE = "</div>";
    public Boolean ADD_FAVOURITE_FLAG = true;

    @Deprecated
    public String ADD_FAVOURITE_DIV = "<div class=\"fav\"><a href=\"#\">" + "<img src=\"css/images/fav-add.png\" border=\"0\" "
            + "/>" + "</a></div>";	// NEW
                                  	// BIHAR
                                  	// ADD
                                  	// FAVOURITE
                                  	// ICON

    public String ADD_FAVOURITE_DIV_CLASS = "div class=\"fav\"";																																// IT
                                                                																																// NEED
                                                                																																// TO
                                                                																																// ADD
                                                                																																// OPEN_TAG
                                                                																																// AND
                                                                																																// CLOSE_TAG
                                                                																																// Externally
    public String ADD_FAVOURITE_DIV_IMG = "<img src=\"css/images/fav-add.png\" border=\"0\" />";
    
    public String FLASHIMAGE = "<img src='./assets/img/flashing-new.png' class='flash-new' />";

}
