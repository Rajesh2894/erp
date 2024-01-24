package com.abm.mainet.common.master.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.dto.BookReceiptDTO;
import com.abm.mainet.common.master.service.BookReceiptService;
import com.abm.mainet.common.master.ui.model.BookReceiptModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/bookreceipt.html")
public class BookReceiptController extends AbstractFormController<BookReceiptModel> {

    final static Logger logger = LoggerFactory.getLogger(BookReceiptController.class);

    @Autowired
    BookReceiptService bookReceiptService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setCommonHelpDocs("bookreceipt.html");
        return index();
    }

    @RequestMapping(params = "addRecieptData", method = { RequestMethod.POST })
    public ModelAndView addReceiptData(final HttpServletRequest request) {
        this.getModel().setBookReceiptDTO(new BookReceiptDTO());
        this.getModel().getBookReceiptDTO().setFormMode(MainetConstants.FlagC);
        return new ModelAndView("BookReceiptform", "command", this.getModel());

    }

    @RequestMapping(params = "create", method = { RequestMethod.POST })
    public ModelAndView create(final HttpServletRequest httpServletRequest) {

        this.getModel().bind(httpServletRequest);

        Long userId = UserSession.getCurrent().getEmployee().getEmpId();

        String ipMac = UserSession.getCurrent().getEmployee().getEmppiservername();
        this.getModel().getBookReceiptDTO().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

        bookReceiptService.saveBookReceiptFormData(this.getModel().getBookReceiptDTO(), userId, ipMac);

        if (this.getModel().getBookReceiptDTO().getFormMode().equals(MainetConstants.FlagC)) {
            this.getModel().setSuccessMessage(getApplicationSession().getMessage("book.save.new"));
        }
        if (this.getModel().getBookReceiptDTO().getFormMode().equals(MainetConstants.FlagU)) {
            this.getModel().setSuccessMessage(getApplicationSession().getMessage("book.save.update"));
        }
        return jsonResult(JsonViewObject
                .successResult(this.getModel().getSuccessMessage()));

    }

    @RequestMapping(params = { "ReceiptBookData" }, produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody JQGridResponse<BookReceiptDTO> getReceiptBookData(final HttpServletRequest httpServletRequest,
            @RequestParam final String page,
            @RequestParam final String rows) {

        if ((this.getModel().getLoadAllRecords()) == true) {
            this.getModel().setReceiptBookDatalist(
                    bookReceiptService.allReceiptBookDataByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
        }
        return getModel().paginate(httpServletRequest, page, rows, this.getModel().getReceiptBookDatalist());
    }

    @RequestMapping(params = "editReceiptBookForm")
    public ModelAndView formForUpdate(@RequestParam("rbId") final Long rbId,
            @RequestParam("flag") final String formMode) {

        populateModel(rbId, formMode);
        return new ModelAndView("BookReceiptform", "command", this.getModel());

    }

    @RequestMapping(params = "getReceiptBookData", method = { RequestMethod.POST })
    public @ResponseBody String getReceiptBookData(final HttpServletRequest request,
            @RequestParam("empId") final Long empId,
            @RequestParam("fayearId") final Long fayearId) {

        String message = "success";

        this.getModel().setLoadAllRecords(false);
        this.getModel().setBookReceiptDTO(null);

        this.getModel().setReceiptBookDatalist(bookReceiptService.getReceiptBookData(empId, fayearId,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        if ((this.getModel().getReceiptBookDatalist().size()) == 0 || this.getModel().getReceiptBookDatalist().isEmpty()) {
            message = "failure";
        }
        return message;
    }

    private void populateModel(final Long rbId, final String formMode) {

        for (BookReceiptDTO bookReceiptDTO : this.getModel().getReceiptBookDatalist())

        {
            if (bookReceiptDTO.getRbId() == rbId) {
                this.getModel().setBookReceiptDTO(bookReceiptDTO);

                break;
            }

        }

        if (formMode.equals(MainetConstants.FlagV)) {
            this.getModel().getBookReceiptDTO().setFormMode(MainetConstants.FlagV);
        } else if (formMode.equals(MainetConstants.FlagU)) {
            this.getModel().getBookReceiptDTO().setFormMode(MainetConstants.FlagU);
            this.getModel().getBookReceiptDTO().setRbId(rbId);

        }
    }

}
