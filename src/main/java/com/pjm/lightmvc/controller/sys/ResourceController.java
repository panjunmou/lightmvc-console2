package com.pjm.lightmvc.controller.sys;

import com.google.common.collect.Lists;
import com.pjm.lightmvc.contants.ControllerMsg;
import com.pjm.lightmvc.contants.GlobalContants;
import com.pjm.lightmvc.controller.base.BaseController;
import com.pjm.lightmvc.service.exception.ServiceException;
import com.pjm.lightmvc.service.sys.ResourceService;
import com.pjm.lightmvc.vo.base.JsonVo;
import com.pjm.lightmvc.vo.base.SessionInfo;
import com.pjm.lightmvc.vo.sys.VResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 */
@Controller
@RequestMapping("/sys/resource")
// TODO: 2016/3/30 所有的方法中都没有考虑到角色的过滤问题
public class ResourceController extends BaseController{

    @Resource
    private ResourceService resourceService;

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "console/sys/resource/resourceList";
    }

    /**
     * 异步加载左侧菜单
     *
     * @param request
     * @param id
     * 左侧菜单的id(数据库中的id的值),当点击父节点的时候easyui会自动将当前节点的id传过来
     * @return
     */
    @RequestMapping("/treeList")
    @ResponseBody
    public List<VResource> treeList(HttpServletRequest request, Long id) {
        try {
            SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalContants.SESSION_INFO);
            List<VResource> vResourceList = resourceService.treeList(sessionInfo, id);
            return vResourceList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    /**
     * 资源管理页面
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<VResource> treeGrid(Long id) throws Exception {
        List<VResource> tree = resourceService.treeGrid(id);
        return tree;
    }

    /**
     * 跳转至新增页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/addPage")
    public String addPage(Model model) throws Exception {
        VResource vResource = new VResource();
        model.addAttribute("resource", vResource);
        return "console/sys/resource/resourceAdd";
    }

    /**
     * 新增资源方法
     * @param vResource
     * @param request
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonVo add(VResource vResource,HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            vResource.setSessionInfo(getCurrentSessionInfo(request));
            VResource resource = resourceService.save(vResource);
            jsonVo.setSuccess(true);
            jsonVo.setObj(resource);
            jsonVo.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.SAVE_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 跳转至修改页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/editPage")
    public String editPage(VResource vResource,Model model) throws Exception {
        VResource resource = resourceService.getVResourceById(vResource.getId());
        model.addAttribute("resource", resource);
        return "console/sys/resource/resourceEdit";
    }

    /**
     * 新增资源方法
     * @param vResource
     * @param request
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonVo edit(VResource vResource,HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            vResource.setSessionInfo(getCurrentSessionInfo(request));
            VResource resource = resourceService.update(vResource);
            jsonVo.setSuccess(true);
            jsonVo.setObj(resource);
            jsonVo.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.SAVE_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 删除
     * @param vResource
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonVo delete(VResource vResource,HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            resourceService.delete(vResource);
            jsonVo.setSuccess(true);
            jsonVo.setMsg(ControllerMsg.DEL_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.DEL_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 状态变更
     * @param vResource
     * @param request
     * @return
     */
    @RequestMapping("/change")
    @ResponseBody
    public JsonVo change(VResource vResource,String statusType,HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            VResource resource = resourceService.change(vResource,statusType);
            jsonVo.setObj(resource);
            jsonVo.setSuccess(true);
            jsonVo.setMsg(ControllerMsg.CHANGE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.CHANGE_MSG_ERROR);
            }
        }
        return jsonVo;
    }
}