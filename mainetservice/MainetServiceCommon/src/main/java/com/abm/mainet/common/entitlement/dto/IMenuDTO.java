/**
 *
 */
package com.abm.mainet.common.entitlement.dto;

import org.springframework.stereotype.Component;

/**
 * @author pabitra.raulo
 */
@Component
public interface IMenuDTO<T> {

    public String MENU_UL_CLOSE = "</ul>";
    public String MENU_LI_OPEN = "<li>";
    public String MENU_LI_CLOSE = "</li>";
    public String OPEN_TAG = "<";
    public String CLOSE_TAG = ">";
    public String MENU_LI_PARENT = "<li><a href =\"javascript:void(0);\" rel=\"";
    public String MENU_UL_PARENT = "<ul id=\"";

    public String MENU_ANCHOR_OPEN = "<a href =";

    public String MENU_ANCHOR_D2K_ONCLICK = "onclick=\" openDialogD2K(";
    public String MENU_ANCHOR_D2K_ONCLICKCLS = "); \" ";
    public String MENU_ANCHOR_CLOSE = "</a>";
    public String ESCAPE_DOUBLE_COUTTE = "\"";
    public String MENU_DIV_CLOSE = "</div>";

    public String ADD_FAVOURITE_DIV_CLASS = "div class=\"fav\"";																																// IT
                                                                																																// Externally
    public String ADD_FAVOURITE_DIV_IMG = "<img src=\"css/images/fav-add.png\" border=\"0\" />";

}
