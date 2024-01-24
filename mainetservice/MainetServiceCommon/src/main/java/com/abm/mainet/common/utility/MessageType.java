package com.abm.mainet.common.utility;

public enum MessageType {

    SUCCESS, INFO, WARNING, DANGER;

    public String getCss() {
        return name().toLowerCase();
    }

}
