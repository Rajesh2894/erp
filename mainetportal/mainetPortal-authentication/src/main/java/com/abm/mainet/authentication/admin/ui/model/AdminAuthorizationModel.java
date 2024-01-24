package com.abm.mainet.authentication.admin.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;

@Component
@Scope(value = "session")
public class AdminAuthorizationModel extends AbstractEntryFormModel<Employee> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private IEmployeeService iEmployeeService;

    private List<Employee> admin = new ArrayList<>(0);

    public List<Employee> getAdmin() {
        return admin;
    }

    public void setAdmin(final List<Employee> admin) {
        this.admin = admin;
    }

    public IEmployeeService getiEmployeeService() {
        return iEmployeeService;
    }

    public void setiEmployeeService(final IEmployeeService iEmployeeService) {
        this.iEmployeeService = iEmployeeService;
    }

    @Override
    protected void initializeModel() {
        super.initializeModel();
    }

}