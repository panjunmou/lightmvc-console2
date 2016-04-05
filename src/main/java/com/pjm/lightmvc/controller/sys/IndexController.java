package com.pjm.lightmvc.controller.sys;

import com.pjm.lightmvc.contants.GlobalContants;
import com.pjm.lightmvc.service.sys.UserService;
import com.pjm.lightmvc.vo.base.JsonVo;
import com.pjm.lightmvc.vo.base.SessionInfo;
import com.pjm.lightmvc.vo.sys.VUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by PanJM on 2016/3/15.
 */
@Controller
@RequestMapping("/console")
public class IndexController {

    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalContants.SESSION_INFO);
        if (sessionInfo != null && sessionInfo.getId() != null) {
            return "/index";
        }
        return "/login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public JsonVo login(VUser userVo, HttpServletRequest request){
        JsonVo jsonVo = new JsonVo();
        try {
            HttpSession session = request.getSession();
            VUser sysUser = userService.login(userVo);
            jsonVo.setSuccess(true);
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setId(sysUser.getId());
            sessionInfo.setLoginName(sysUser.getLoginName());
            sessionInfo.setUserName(sysUser.getUserName());
            // TODO: 2016/3/16 缺少激斗,区域,角色的设置
            session.setAttribute(GlobalContants.SESSION_INFO, sessionInfo);
            // TODO: 2016/3/16 缺少记录登录操作的日志
        } catch (Exception e) {
            jsonVo.setSuccess(false);
            jsonVo.setMsg(e.getMessage());
        }
        return jsonVo;
    }
}
