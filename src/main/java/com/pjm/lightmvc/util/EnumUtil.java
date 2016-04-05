package com.pjm.lightmvc.util;

import com.pjm.lightmvc.contants.base.BaseEnum;
import com.pjm.lightmvc.vo.base.EnumVo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/3/29.
 * 枚举工具类
 */
public class EnumUtil {

    public static List<EnumVo> getEnumList(Class clazz) {
        List<EnumVo> enumVoList = new ArrayList<>();
        try {
            Method method = clazz.getMethod("values");
            BaseEnum enums[] = (BaseEnum[]) method.invoke(null, null);
            for (BaseEnum anEnum : enums) {
                EnumVo enumVo = new EnumVo(anEnum.getValue(), anEnum.getLabel());
                enumVoList.add(enumVo);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return enumVoList;
    }
}
