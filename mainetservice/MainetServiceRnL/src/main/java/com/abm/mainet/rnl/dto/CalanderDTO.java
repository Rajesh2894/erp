package com.abm.mainet.rnl.dto;

import java.util.Date;
import java.util.List;

public class CalanderDTO {
    private Long id;
    private Date start;
    private String title;
    private String className;
    private Date end;
    private String desc;
    private List<String> datesList;

    public CalanderDTO() {

    }

    /**
     * @param id
     * @param start
     * @param title
     * @param className
     * @param end
     * @param desc
     */
    public CalanderDTO(final Long id, final Date start, final String title, final String className,
            final Date end, final String desc) {
        super();
        this.id = id;
        this.start = start;
        this.title = title;
        this.className = className;
        this.end = end;
        this.desc = desc;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(final Date start) {
        this.start = start;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(final String className) {
        this.className = className;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(final Date end) {
        this.end = end;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(final String desc) {
        this.desc = desc;
    }

    /**
     * @return the datesList
     */
    public List<String> getDatesList() {
        return datesList;
    }

    /**
     * @param datesList the datesList to set
     */
    public void setDatesList(final List<String> datesList) {
        this.datesList = datesList;
    }
}
