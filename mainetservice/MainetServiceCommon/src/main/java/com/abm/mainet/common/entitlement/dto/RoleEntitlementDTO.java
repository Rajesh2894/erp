/**
 *
 */
package com.abm.mainet.common.entitlement.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public class RoleEntitlementDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String add;
    private String update;
    private String delete;
    private String edit;
    List<RoleEntitlementDTO> roleEntitlementDTOList;

    /**
     * @param id
     * @param add
     * @param update
     * @param delete
     * @param edit
     */

    /**
     *
     */
    public RoleEntitlementDTO() {
        super();
    }

    /**
     * @param id
     * @param name
     * @param add
     * @param update
     * @param delete
     * @param edit
     * @param roleEntitlementDTOList
     */
    public RoleEntitlementDTO(final Long id, final String name,
            final String add, final String update, final String delete,
            final String edit,
            final List<RoleEntitlementDTO> roleEntitlementDTOList) {
        super();
        this.id = id;
        this.name = name;
        this.add = add;
        this.update = update;
        this.delete = delete;
        this.edit = edit;
        this.roleEntitlementDTOList = roleEntitlementDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(final String add) {
        this.add = add;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(final String update) {
        this.update = update;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(final String delete) {
        this.delete = delete;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(final String edit) {
        this.edit = edit;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<RoleEntitlementDTO> getRoleEntitlementDTOList() {
        return roleEntitlementDTOList;
    }

    public void setRoleEntitlementDTOList(
            final List<RoleEntitlementDTO> roleEntitlementDTOList) {
        this.roleEntitlementDTOList = roleEntitlementDTOList;
    }

}
