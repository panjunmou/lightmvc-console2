package com.pjm.lightmvc.controller.base;

import com.pjm.lightmvc.contants.GlobalContants;
import com.pjm.lightmvc.vo.base.SessionInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/3/30.
 */
public class BaseController {

    protected SessionInfo getCurrentSessionInfo(HttpServletRequest request) {
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(GlobalContants.SESSION_INFO);
        return sessionInfo;
    }

}
