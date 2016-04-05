package com.pjm.lightmvc.service.sys;

import com.pjm.lightmvc.vo.sys.VArea;

import java.util.List;

/**
 * Created by PanJM on 2016/4/5
 */
public interface AreaService {
    List<VArea> treeGrid(Long id) throws Exception;

    VArea save(VArea vArea) throws Exception;

    VArea getVResourceById(Long id) throws Exception;

    VArea update(VArea vArea) throws Exception;

    void delete(VArea vArea) throws Exception;
}
