package com.abm.mainet.water.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.water.domain.TBCsmrrCmdMas;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;

@Component
@Scope("session")
public class NewWaterConnectionDetailsModel extends AbstractFormModel {

    private static final long serialVersionUID = -7087904014932198341L;

    private TbKCsmrInfoMH csmrInfo = new TbKCsmrInfoMH();

    private TBCsmrrCmdMas csmrrCmd = new TBCsmrrCmdMas();

    /**
     * @return the csmrInfo
     */
    public TbKCsmrInfoMH getCsmrInfo() {
        return csmrInfo;
    }

    /**
     * @param csmrInfo the csmrInfo to set
     */
    public void setCsmrInfo(final TbKCsmrInfoMH csmrInfo) {
        this.csmrInfo = csmrInfo;
    }

    /**
     * @return the csmrrCmd
     */
    public TBCsmrrCmdMas getCsmrrCmd() {
        return csmrrCmd;
    }

    /**
     * @param csmrrCmd the csmrrCmd to set
     */
    public void setCsmrrCmd(final TBCsmrrCmdMas csmrrCmd) {
        this.csmrrCmd = csmrrCmd;
    }

}
