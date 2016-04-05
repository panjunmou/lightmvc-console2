package com.pjm.lightmvc.service.sys;

import com.pjm.lightmvc.vo.base.SessionInfo;
import com.pjm.lightmvc.vo.sys.VResource;

import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 */
public interface ResourceService {
    List<VResource> treeList(SessionInfo sessionInfo, Long pId) throws Exception;

    List<VResource> treeGrid(Long pId) throws Exception;

    VResource save(VResource vResource) throws Exception;

    VResource getVResourceById(Long id) throws Exception;

    VResource update(VResource vResource) throws Exception;

    void delete(VResource vResource) throws Exception;

    VResource change(VResource vResource,String statusType) throws Exception;
}
