package com.abm.mainet.common.workflow.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.mapper.TbDepartmentServiceMapper;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.EventDTO;
import com.abm.mainet.common.workflow.dto.EventMasterResponse;
import com.abm.mainet.common.workflow.dto.ServicesEventDTO;
import com.abm.mainet.common.workflow.service.EventMasterService;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;

@Controller
@RequestMapping("/EventMaster.html")
public class EventMasterController extends AbstractController {

    private static Logger LOG = Logger.getLogger(EventMasterController.class);
    public static final String ORG_LIST = "orgList";
    public static final String EVENT_LIST = "eventList";
    public static final String DEP_LIST = "depList";
    private static final String JSP_LIST = "eventMaster/list";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM_ERROR = "workflowHomePageException";

    private String activeClass;

    @Resource
    private EventMasterService eventMasterService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private TbDepartmentServiceMapper tbDepartmentServiceMapper;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Resource
    private IWorkFlowTypeService workFlowTypeService;

    private static final String REQUESTDTO_CANT_NULL = "dto can not be null";

    Logger logger = Logger.getLogger(EventMasterController.class);

    private List<EventDTO> list;
    private List<String> eventsMapList;

    public EventMasterController() {
        super(EventMasterController.class, MainetConstants.EVENT_MASTER);

    }

    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        list = new ArrayList<>();
        final List<TbDepartment> deptList = populateDepartment();
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
        return JSP_LIST;
    }

    @RequestMapping(params = "form")
    public String index(final HttpServletRequest request, final Model model,
            @RequestParam("dpDeptId") final Long deptId,
            @RequestParam("serviceId") final Long serviceId) {

        final UserSession userSession = UserSession.getCurrent();
        final Organisation organisation = new Organisation();
        final ServicesEventDTO dto = new ServicesEventDTO();
        final Department deptEntity = new Department();

        organisation.setOrgid(userSession.getOrganisation().getOrgid());
        dto.setOrgId(organisation);
        List<Object[]> eventsList = null;
        eventsList = eventMasterService.findEventList(MainetConstants.MENU.EndWithHTML, MainetConstants.MENU._0);
        final List<TbDepartment> deptList = populateDepartment();
       
	final List<Object[]> smfEventsList = new ArrayList<>();
	eventsList.stream().forEach(event -> {
	    StringBuilder sb = new StringBuilder();

	    final String smfname8 = (String) event[8];
	    final String smfname7 = (String) event[7];
	    final String smfname6 = (String) event[6];
	    final String smfname5 = (String) event[5];
	    final String smfname4 = (String) event[4];
	    final String smfname3 = (String) event[3];
	    final Long smfId = (Long) event[0];

	    if (smfname8 != null) 
		sb.append("[").append(smfname8).append("]>>");	    
	    if (smfname7 != null) 
		sb.append("[").append(smfname7).append("]>>");	    
	    if (smfname6 != null) 
		sb.append("[").append(smfname6).append("]>>");	    
	    if (smfname5 != null) 
		sb.append("[").append(smfname5).append("]>>");	    
	    if (smfname4 != null) 
		sb.append("[").append(smfname4).append("]>>");	    
	    if (smfname3 != null) 
		sb.append("[").append(smfname3).append("]");	    

	    Object[] events = { smfId, sb.toString() };
	    smfEventsList.add(events);
	}); 
        
        
        final TbDepartment deptBean = tbDepartmentService.findById(deptId);
        dto.setSmServiceId(serviceId);
        tbDepartmentServiceMapper.mapTbDepartmentToTbDepartmentEntity(deptBean, deptEntity);
        dto.setDeptId(deptEntity);
        final List<Object[]> serviceList = populateServices(deptId, request, model);

        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
        model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, FormMode.CREATE);
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, this);
        model.addAttribute(MainetConstants.SERVICES_EVENT_DTO, dto);
        model.addAttribute(MainetConstants.EVENTS_NOT_SELECTED, smfEventsList);
        model.addAttribute(MainetConstants.SERVICE_LIST, serviceList);
        model.addAttribute(MainetConstants.ACTIVE_CLASS, activeClass);
        return MainetConstants.EVENT_MASTER;
    }

    @RequestMapping(params = "formforUpdate", method = RequestMethod.POST)
    public String formforUpdate(final HttpServletRequest request, final Model model,
            @RequestParam("deptId") final Long deptId,
            @RequestParam("smserviceId") final Long smserviceId,
            @RequestParam("mode") final String mode) {

        log("Action 'formforUpdate'");
        eventsMapList = new ArrayList<>();

        final Long sessionOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation organisation = new Organisation();
        organisation.setOrgid(sessionOrgId);

        final Department deptEntity = new Department();
        final ServicesEventDTO bean = new ServicesEventDTO();
        final List<TbDepartment> deptList = populateDepartment();
        final List<Object[]> serviceList = populateServices(deptId, request, model);

        bean.setSmServiceId(smserviceId);
        bean.setOrgId(organisation);

        final TbDepartment department = tbDepartmentService.findById(deptId);
        tbDepartmentServiceMapper.mapTbDepartmentToTbDepartmentEntity(department, deptEntity);
        bean.setDeptId(deptEntity);

        final List<Object[]> eventsSelected = eventMasterService.findMappedEventList(MainetConstants.MENU.EndWithHTML,
                MainetConstants.MENU._0, deptId, smserviceId, sessionOrgId);
        final List<Object[]> eventsNotSelected = eventMasterService.findNonMappedEventList(MainetConstants.MENU.EndWithHTML,
                MainetConstants.MENU._0, deptId, smserviceId, sessionOrgId);
        
	final List<Object[]> smfEventsNotSelected = new ArrayList<>();
	eventsNotSelected.stream().forEach(event -> {
	    StringBuilder sb = new StringBuilder();

	    final String smfname7 = (String) event[7];
	    final String smfname6 = (String) event[6];
	    final String smfname5 = (String) event[5];
	    final String smfname4 = (String) event[4];
	    final String smfname3 = (String) event[3];
	    final String smfname2 = (String) event[2];
	    final Long smfId = (Long) event[0];

	    if (smfname7 != null) 
		sb.append("[").append(smfname7).append("]>>");	    
	    if (smfname6 != null) 
		sb.append("[").append(smfname6).append("]>>");	    
	    if (smfname5 != null) 
		sb.append("[").append(smfname5).append("]>>");	    
	    if (smfname4 != null) 
		sb.append("[").append(smfname4).append("]>>");	    
	    if (smfname3 != null) 
		sb.append("[").append(smfname3).append("]>>");	    
	    if (smfname2 != null) 
		sb.append("[").append(smfname2).append("]");
	    
	    Object[] events = { smfId, sb.toString() };
	    smfEventsNotSelected.add(events);
	});

	final List<Object[]> smfEventsSelected = new ArrayList<>();

	eventsSelected.stream().forEach(event -> {
	    StringBuilder sb = new StringBuilder();

	    final String smfname8 = (String) event[8];
	    final String smfname7 = (String) event[7];
	    final String smfname6 = (String) event[6];
	    final String smfname5 = (String) event[5];
	    final String smfname4 = (String) event[4];
	    final String smfname3 = (String) event[3];
	    final Long smfId = (Long) event[0];

	    if (smfname8 != null) 
		sb.append("[").append(smfname8).append("]>>");	    
	    if (smfname7 != null) 
		sb.append("[").append(smfname7).append("]>>");	    
	    if (smfname6 != null) 
		sb.append("[").append(smfname6).append("]>>");	    
	    if (smfname5 != null) 
		sb.append("[").append(smfname5).append("]>>");	    
	    if (smfname4 != null) 
		sb.append("[").append(smfname4).append("]>>");	    
	    if (smfname3 != null) 
		sb.append("[").append(smfname3).append("]");	    
	    
	    Object[] events = { smfId, sb.toString() };
	    smfEventsSelected.add(events);
	});

        model.addAttribute(MainetConstants.EVENTS_SELECTED_LST, smfEventsSelected);
        model.addAttribute(MainetConstants.EVENTS_NOT_SELECTED, smfEventsNotSelected);
        for (final Object[] dto : eventsSelected) {
            eventsMapList.add(dto[0].toString());
        }

        if (mode.equals(MainetConstants.EDIT)) {
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, FormMode.EDIT);
        } else {
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, FormMode.VIEW);
        }

        model.addAttribute(MainetConstants.CommonConstants.COMMAND, this);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
        model.addAttribute(MainetConstants.SERVICE_LIST, serviceList);
        model.addAttribute(MainetConstants.SERVICES_EVENT_DTO, bean);
        model.addAttribute(MainetConstants.ACTIVE_CLASS, activeClass);
        return MainetConstants.EVENT_MASTER;
    }

    @RequestMapping(params = "department")
    public @ResponseBody List<TbDepartment> populateDepartment() {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbDepartment> deptList = tbDepartmentService.findActualDept(orgId);
        return deptList;
    }

    /**
     * list of services available in Department
     * @param orgId
     * @param deptId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "services", method = RequestMethod.POST)
    public @ResponseBody List<Object[]> populateServices(@RequestParam("deptId") final Long deptId,
            final HttpServletRequest request, final Model model) {
        LOG.info("start the services ");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        return serviceMasterService.findAllActiveServicesForDepartment(orgId, deptId);
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public @ResponseBody String createEventMaste(@Valid final ServicesEventDTO serviceEventDto, final BindingResult bindingResult,
            final HttpServletRequest request, final Model model) {

        String isBeingSaved = MainetConstants.MENU.FALSE;
        Assert.notNull(serviceEventDto, REQUESTDTO_CANT_NULL);

        if (!bindingResult.hasErrors()) {

            try {
                final UserSession userSession = UserSession.getCurrent();
                final boolean status = eventMasterService.saveOrUpdateEventMaster(serviceEventDto, userSession.getEmployee(),
                        userSession.getOrganisation());

                if (status == true) {
                    isBeingSaved = MainetConstants.MENU.TRUE;
                }
            } catch (final Exception ex) {
                logger.info(ex);
                isBeingSaved = JSP_FORM_ERROR;
            }

        } else {
            isBeingSaved = JSP_LIST;
        }

        return isBeingSaved;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public @ResponseBody boolean updateEvents(@Valid final ServicesEventDTO serviceEventDto, final BindingResult bindingResult,
            final HttpServletRequest request, final Model model) {

        final Organisation org = UserSession.getCurrent().getOrganisation();
        final Employee emp = UserSession.getCurrent().getEmployee();
        boolean response = false;

        int counter = 0;
        final List<String> newList = new ArrayList<>();
        final List<String> deletedList = new ArrayList<>();

        for (final String curEventsId : serviceEventDto.getEventMapId()) {
            for (final String eventsId : eventsMapList) {
                if (!curEventsId.equals(eventsId)) {
                    counter++;
                } else {
                    counter = 0;
                    break;
                }
            }
            if (counter > 0) {
                newList.add(curEventsId);
            }
        }

        counter = 0;
        for (final String curEventsId : eventsMapList) {
            for (final String eventsId : serviceEventDto.getEventMapId()) {
                if (!curEventsId.equals(eventsId)) {
                    counter++;
                } else {
                    counter = 0;
                    break;
                }
            }
            if (counter > 0) {
                deletedList.add(curEventsId);
            }
        }
        if (!deletedList.isEmpty() && deletedList != null) {
            boolean deletedEventMapped = isDeletedEventMapped(deletedList, org.getOrgid(),
                    serviceEventDto.getDeptId().getDpDeptid(),
                    serviceEventDto.getSmServiceId());
            if (!deletedEventMapped)
                response = updateEventMaster(serviceEventDto, org, emp, newList, deletedList);

        } else {
            response = updateEventMaster(serviceEventDto, org, emp, newList, deletedList);
        }
        return response;

    }

    private boolean updateEventMaster(final ServicesEventDTO serviceEventDto, final Organisation org, final Employee emp,
            final List<String> newList, final List<String> deletedList) {
        return eventMasterService.updateEventsMapping(serviceEventDto, newList, deletedList, org, emp);
    }

    private boolean isDeletedEventMapped(List<String> deletedList, long orgId, Long deptId, Long serviceId) {
        List<Long> idsList = deletedList.stream().map(Long::parseLong).collect(Collectors.toList());
        return workFlowTypeService.isEventMapped(idsList, orgId, deptId, serviceId);
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody EventMasterResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");

        final EventMasterResponse response = new EventMasterResponse();

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(list);
        response.setTotal(list.size());
        response.setRecords(list.size());
        response.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, list);

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, params = "delete") // GET or POST
    public @ResponseBody void delete(@RequestParam("serviceEventId") final Long serviceEventId,
            @RequestParam("isDeleted") final String isDeleted) {
        log("Action 'delete'");
        eventMasterService.delete(isDeleted, serviceEventId);
    }

    @RequestMapping(params = "existServiceEvent", method = RequestMethod.POST)
    public @ResponseBody String checkEventsAlreadyExist(@Valid final ServicesEventDTO serviceEventDto,
            final BindingResult bindingResult,
            final HttpServletRequest request, final Model model) {

        String response = MainetConstants.MENU.FALSE;
        if (!bindingResult.hasErrors()) {
            final boolean status = eventMasterService.checkEventsForServiceIdExist(serviceEventDto, MainetConstants.MENU.N);
            if (status == true) {
                response = MainetConstants.MENU.TRUE;
            }

        } else {
            logger.info("Object Binding problems");
            response = MainetConstants.INTERNAL_ERROR;
        }

        return response;
    }

    @RequestMapping(params = "searchEvents", method = RequestMethod.POST)
    public @ResponseBody int searchEvents(@RequestParam("dpDeptId") final Long deptId,
            @RequestParam("serviceId") final Long serviceId) {
        log("Action 'searchEvents'");
        int count = 0;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        list = eventMasterService.findEventsByDeptOrgService(deptId, orgId, serviceId);
        count = list.size();
        return count;
    }

    @RequestMapping(params = "validateEvent", method = RequestMethod.POST)
    public @ResponseBody boolean validateEvent(@RequestParam("dpDeptId") final Long deptId,
            @RequestParam("serviceId") final Long serviceId) {
        List<EventDTO> eventsList = new ArrayList<>();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        boolean isValid = true;
        eventsList = eventMasterService.findEventsByDeptOrgService(deptId, orgId, serviceId);
        if (eventsList.size() > 0) {
            isValid = false;
        }
        return isValid;
    }

}
