
package com.abm.mainet.account.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.utility.AbstractServiceMapper;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class AccountReceiptEntryServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public AccountReceiptEntryServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TbServiceReceiptMasBean mapTbServiceReceiptMasEntityToTbServiceReceiptMasBean(
            final TbServiceReceiptMasEntity tbServiceReceiptMasEntity) {
        if (tbServiceReceiptMasEntity == null) {
            return null;
        }

        // --- Generic mapping

        final TbServiceReceiptMasBean tbServiceReceiptMasBean = map(tbServiceReceiptMasEntity, TbServiceReceiptMasBean.class);

        return tbServiceReceiptMasBean;
    }

    public void mapTbServiceReceiptMasBeanToTbServiceReceiptMasEntity(final TbServiceReceiptMasBean tbServiceReceiptMasBean,
            final TbServiceReceiptMasEntity tbServiceReceiptMasEntity) {
        if (tbServiceReceiptMasBean == null) {
            return;
        }

        // --- Generic mapping
        map(tbServiceReceiptMasBean, tbServiceReceiptMasEntity);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

}