package com.pjm.lightmvc.service.sys.impl;

import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.contants.UseStatus;
import com.pjm.lightmvc.contants.sys.resource.ResourceStatus;
import com.pjm.lightmvc.dao.sys.ResourceDao;
import com.pjm.lightmvc.model.sys.TResource;
import com.pjm.lightmvc.service.sys.ResourceService;
import com.pjm.lightmvc.vo.base.SessionInfo;
import com.pjm.lightmvc.vo.sys.VResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by PanJM on 2016/3/25.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceDao resourceDao;

    /**
     * 异步加载资源列表
     * 状态:启动
     * 资源类型:菜单
     *
     * @param sessionInfo
     * @param pId         父节点ID
     * @return
     * @throws Exception
     */
    @Override
    public List<VResource> treeList(SessionInfo sessionInfo, Long pId) throws Exception {
        List<TResource> resourceList = null;
        List<VResource> vResourceList = new ArrayList<>();

        if (sessionInfo != null) {
            if ("admin".equals(sessionInfo.getLoginName())) {
                //管理员查找所有启用的资源列表
                List<Condition> conditionList = new ArrayList<>();
                //启用
                conditionList.add(new Condition("status", Integer.parseInt(UseStatus.ACTIVITY.getValue()), Condition.EQUAL_TO));
                conditionList.add(new Condition("type", new Integer[]{Integer.parseInt(ResourceStatus.MENU.getValue()),Integer.parseInt(ResourceStatus.PAGE.getValue())} , Condition.IN));
                if (pId == null) {
                    //所有父节点
                    conditionList.add(new Condition("parent", Condition.ISNULL));
                } else {
                    //相应pid下面的所有资源
                    conditionList.add(new Condition("parent.treeId", pId, Condition.EQUAL_TO));
                }
                //设置ORDERBY
                PageModel pageModel = new PageModel();
                pageModel.setSort("seq");
                pageModel.setOrder("ASC");
                //查询
                resourceList = resourceDao.getPageResult(conditionList, pageModel).getReultList();
            } else {
                // TODO: 2016/3/25 查找相应人员的资源权限
            }
        } else {
            return null;
        }

        //将资源转换为Tree对象,以便页面的展示
        if (resourceList != null && resourceList.size() > 0) {
            //遍历资源列表
            for (TResource resource : resourceList) {
                VResource vResource = new VResource();
                copyRealToVo(resource, vResource,false);
                //增加
                vResourceList.add(vResource);
            }
        }
        return vResourceList;
    }

    /**
     * 加载资源管理的列表
     *
     * @param pId
     * @return
     * @throws Exception
     */
    @Override
    public List<VResource> treeGrid(Long pId) throws Exception {
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
        List<TResource> resourceList = resourceDao.getPageResult(conditionList, pageModel).getReultList();
        List<VResource> resourceVoList = new ArrayList<>();
        for (int i = 0; i < resourceList.size(); i++) {
            TResource resource = resourceList.get(i);
            VResource resourceVo = copyRealToVo(resource, new VResource(),true);
            resourceVoList.add(resourceVo);
        }
        return resourceVoList;
    }

    /**
     * 保存资源
     *
     * @param vResource
     * @throws Exception
     */
    @Override
    public VResource save(VResource vResource) throws Exception {
        //保存基本元素
        TResource tResource = new TResource();
        BeanUtils.copyProperties(vResource, tResource);
        resourceDao.save(tResource);

        //更新其它元素
        String pId = vResource.getpId();
        //设置父节点
        if (pId != null && !"".equals(pId)) {
            TResource parent = resourceDao.findByTreeId(Long.parseLong(pId));
            tResource.setParent(parent);
            tResource.setDescription(parent.getDescription() + "-" + tResource.getName());
        } else {
            tResource.setDescription(tResource.getName());
        }
        //设置TreeId
        tResource.setTreeId(tResource.getId() + 10000l);
        tResource.setCreateUser(vResource.getSessionInfo().getLoginName());
        resourceDao.update(tResource);

        //设置locationTree,用于前台的定位
        vResource = locationResource(tResource, vResource);

        return vResource;
    }

    /**
     * 根据id获取Resource对象,并将其转换成VO对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public VResource getVResourceById(Long id) throws Exception {
        TResource tResource = resourceDao.find(id);
        VResource vResource = copyRealToVo(tResource, new VResource(),false);
        return vResource;
    }

    /**
     * 更新资源
     *
     * @param vResource
     * @return
     * @throws Exception
     */
    @Override
    public VResource update(VResource vResource) throws Exception {
        Long id = vResource.getId();
        TResource tResource = resourceDao.find(id);
        copyVoToReal(vResource, tResource);

        //新上级资源的TreeId
        String treeIdStr = vResource.getpId();
        if (treeIdStr != null && !"".equals(treeIdStr)) {
            TResource newParent = resourceDao.findByTreeId(Long.parseLong(treeIdStr));
            tResource.setParent(newParent);
            tResource.setDescription(tResource.getParent().getDescription() + "-" + tResource.getName());
        } else {
            tResource.setParent(null);
            tResource.setDescription(tResource.getName());
        }
        tResource.setUpdateUser(vResource.getSessionInfo().getLoginName());
        resourceDao.update(tResource);
        copyRealToVo(tResource, vResource,true);
        return vResource;
    }

    @Override
    public void delete(VResource vResource) throws Exception {
        Long id = vResource.getId();
        resourceDao.delete(id);
    }

    /**
     * 将真实对象复制到VO对象
     * @param tResource
     * @param vResource
     * @param showLeaf 是否显示按钮
     * @return
     */
    private VResource copyRealToVo(TResource tResource, VResource vResource,boolean showLeaf) {
        BeanUtils.copyProperties(tResource, vResource);
        //设置点击的url连接
        Map<String, Object> map = new HashMap<>();
        map.put("url", tResource.getUrl());
        vResource.setAttributes(map);
        vResource = locationResource(tResource, vResource);
        if (tResource.getParent() != null) {
            vResource.setpId(tResource.getParent().getTreeId().toString());
        }
        Set<TResource> children = tResource.getChildren();
        if (children != null && children.size() > 0) {
            String state = "open";
            if (showLeaf) {
                state = "closed";
            }else{
                for (TResource child : children) {
                    if (child.getType() == Integer.parseInt(ResourceStatus.MENU.getValue()) || child.getType() == Integer.parseInt(ResourceStatus.PAGE.getValue())) {
                        state = "closed";
                        break;
                    }
                }
            }
            vResource.setState(state);
        }
        return vResource;
    }

    /**
     * 将VO对象复制到真实对象
     *
     * @param vResource
     * @return
     */
    private TResource copyVoToReal(VResource vResource, TResource tResource) {
        BeanUtils.copyProperties(vResource, tResource);
        return tResource;
    }

    /**
     * 设置locationTree字段和rootId字段
     * 用于页面的定位
     *
     * @param tResource
     * @param vResource
     * @return
     */
    private VResource locationResource(TResource tResource, VResource vResource) {
        //组装locationTree字段,用于定位节点位置;
        StringBuffer locationTree = new StringBuffer("");
        TResource parent = tResource.getParent();
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
        locationTree.append(tResource.getTreeId());
        vResource.setLocationTree(locationTree.toString());
        return vResource;
    }

    /**
     * 变更状态
     * @param vResource
     * @throws Exception
     */
    @Override
    public VResource change(VResource vResource,String statusType) throws Exception {
        TResource tResource = resourceDao.find(vResource.getId());
        List<TResource> resourceList = new ArrayList<>();
        recursionResource(tResource, resourceList);
        String value = "";
        if (statusType.equals("start")) {
            value = UseStatus.ACTIVITY.getValue();
        }else{
            value = UseStatus.UNACTIVITI.getValue();
        }
        for (int i = 0; i < resourceList.size(); i++) {
            TResource resource = resourceList.get(i);
            resource.setStatus(Integer.parseInt(value));
            resourceDao.update(resource);
        }
        copyRealToVo(tResource, vResource,true);
        return vResource;
    }

    /**
     * 递归资源
     * @param tResource
     * @param tResourceList
     */
    private void recursionResource(TResource tResource,List<TResource> tResourceList) {
        if (tResource != null) {
            tResourceList.add(tResource);
            for (TResource resource : tResource.getChildren()) {
                tResourceList.add(resource);
                if (resource.getChildren() != null && resource.getChildren().size() > 0) {
                    recursionResource(resource,tResourceList);
                }
            }
        }
    }
}
