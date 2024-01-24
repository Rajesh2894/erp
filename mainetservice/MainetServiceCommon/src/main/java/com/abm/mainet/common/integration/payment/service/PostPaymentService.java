package com.abm.mainet.common.integration.payment.service;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.utility.ApplicationContextProvider;

@Primary
@Service
public class PostPaymentService implements IPostPaymentService {
    
    @Resource
    private DepartmentService departmentService;
    
    
    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public void postPaymentSuccess(final CommonChallanDTO CommonChallanDTO ,TbServiceReceiptMasEntity receiptMaster ) {
        try {
            IPostPaymentService paymentServiceInstance = null;
            String serviceClassName = null;
            final String dept = departmentService.getDeptCode(CommonChallanDTO.getDeptId());
            serviceClassName = messageSource.getMessage("postPayment." + dept, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            if (serviceClassName != null && !serviceClassName.isEmpty()) {
                paymentServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                        IPostPaymentService.class);
                if (paymentServiceInstance != null) {
                    paymentServiceInstance.postPaymentSuccess(CommonChallanDTO,receiptMaster);
                }
            }
        } catch (LinkageError | Exception e) {
            throw new FrameworkException(
                    "Exception in Post payment success for advance payment for department Id :" + CommonChallanDTO.getDeptId(),
                    e);
        }
    }

    @Override
    @Transactional
    public void postPaymentFailure(final CommonChallanDTO CommonChallanDTO) {
        try {
            IPostPaymentService paymentServiceInstance = null;
            String serviceClassName = null;
            final String dept = departmentService.getDeptCode(CommonChallanDTO.getDeptId());
            serviceClassName = messageSource.getMessage("postPayment." + dept, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            if (serviceClassName != null && !serviceClassName.isEmpty()) {
                paymentServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                        IPostPaymentService.class);
                if (paymentServiceInstance != null) {
                    paymentServiceInstance.postPaymentFailure(CommonChallanDTO);
                }
            }
        } catch (LinkageError | Exception e) {
            throw new FrameworkException(
                    "Exception in Post payment success for advance payment for department Id :" + CommonChallanDTO.getDeptId(),
                    e);
        }
    }

   
}
