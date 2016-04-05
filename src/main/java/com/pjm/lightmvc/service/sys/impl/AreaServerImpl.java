package com.pjm.lightmvc.service.sys.impl;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.dao.sys.AreaDao;
import com.pjm.lightmvc.model.sys.TArea;
import com.pjm.lightmvc.service.sys.AreaService;
import com.pjm.lightmvc.vo.sys.VArea;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/4/5.
 */
@Service
public class AreaServerImpl implements AreaService {

    @Resource
    private AreaDao areaDao;

    @Override
    public List<VArea> treeGrid(Long pId) throws Exception {
        //管理员查找所有启用的资源列表
        List<Condition> conditionList = new ArrayList<>();
        if (pId == null) {
            //所有父节点
            conditionList.add(new Condition("parent", Condition.ISNULL));
        } else {
            //相应pid下面的所有资源
            conditionList.add(new Condition("parent.treeId", pId, Condition.EQUAL_TO));
        }
        PageModel pageModel = new PageModel();
        pageModel.setSort("seq");
        pageModel.setOrder("asc");
        List<TArea> resourceList = areaDao.getPageResult(conditionList, pageModel).getReultList();
        List<VArea> resourceVoList = new ArrayList<>();
        for (int i = 0; i < resourceList.size(); i++) {
            TArea tArea = resourceList.get(i);
            VArea vArea = copyRealToVo(tArea, new VArea());
            resourceVoList.add(vArea);
        }
        return resourceVoList;
    }


    @Override
    public VArea save(VArea vArea) throws Exception {
        //保存基本元素
        TArea tArea = new TArea();
        BeanUtils.copyProperties(tArea, vArea);
        areaDao.save(tArea);

        //更新其它元素
        String pId = vArea.getpId();
        //设置父节点
        if (pId != null && !"".equals(pId)) {
            TArea parent = areaDao.findByTreeId(Long.parseLong(pId));
            tArea.setParent(parent);
        }
        //设置TreeId
        tArea.setTreeId(tArea.getId() + 10000l);
        tArea.setCreateUser(vArea.getSessionInfo().getLoginName());
        areaDao.update(tArea);

        //设置locationTree,用于前台的定位
        vArea = locationResource(tArea, vArea);
        return vArea;
    }

    @Override
    public VArea getVResourceById(Long id) throws Exception {
        TArea tArea = areaDao.find(id);
        VArea vArea = copyRealToVo(tArea, new VArea());
        return vArea;
    }

    @Override
    public VArea update(VArea vArea) throws Exception {
        Long id = vArea.getId();
        TArea tArea = areaDao.find(id);
        copyVoToReal(vArea, tArea);

        //新上级资源的TreeId
        String treeIdStr = vArea.getpId();
        if (treeIdStr != null && !"".equals(treeIdStr)) {
            TArea newParent = areaDao.findByTreeId(Long.parseLong(treeIdStr));
            tArea.setParent(newParent);
        } else {
            tArea.setParent(null);
        }
        tArea.setUpdateUser(vArea.getSessionInfo().getLoginName());
        areaDao.update(tArea);
        copyRealToVo(tArea, vArea);
        return vArea;
    }

    private void copyVoToReal(VArea vArea, TArea tArea) {
        BeanUtils.copyProperties(vArea, tArea);
    }

    @Override
    public void delete(VArea vArea) throws Exception {
        Long id = vArea.getId();
        areaDao.delete(id);
    }

    private VArea copyRealToVo(TArea tArea, VArea vArea) {
        BeanUtils.copyProperties(tArea, vArea);
        vArea = locationResource(tArea, vArea);
        if (tArea.getParent() != null) {
            vArea.setpId(tArea.getParent().getTreeId().toString());
        }
        vArea.setState("closed");
        return vArea;
    }

    /**
     * 设置locationTree字段和rootId字段
     * 用于页面的定位
     *
     * @param tArea
     * @param vArea
     * @return
     */
    private VArea locationResource(TArea tArea, VArea vArea) {
        //组装locationTree字段,用于定位节点位置;
        StringBuffer locationTree = new StringBuffer("");
        TArea parent = tArea.getParent();
        if (parent != null) {
            locationTree.insert(0, parent.getTreeId().toString()).insert(0, ",");
            while (parent.getParent() != null) {
                parent = parent.getParent();
                locationTree.insert(0, parent.getTreeId().toString()).insert(0, ",");
            }
        }
        if (locationTree.length() > 0) {
            locationTree.deleteCharAt(0);
            locationTree.append(",");
        }
        locationTree.append(tArea.getTreeId());
        vArea.setLocationTree(locationTree.toString());
        return vArea;
    }
}
