package com.abm.mainet.common.utility;

import java.io.Serializable;

/**
 * @author pabitra.raulo
 * @Since 21-Jan-2014
 */
public class FavouriteMenuAsJson implements Serializable {

    private static final long serialVersionUID = 5933470080310423868L;
    private String result;
    private StringBuffer html;

    public String getResult() {
        return result;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public StringBuffer getHtml() {
        return html;
    }

    public void setHtml(final StringBuffer html) {
        this.html = html;
    }

}
