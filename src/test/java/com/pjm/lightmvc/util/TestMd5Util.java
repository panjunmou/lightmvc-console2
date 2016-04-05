package com.pjm.lightmvc.util;

import org.junit.Test;

/**
 * Created by PanJM on 2016/3/16.
 */
public class TestMd5Util {
    @Test
    public void tsetMd5() {
        String md5 = MD5Util.md5("admin");
        System.out.println("md5 = " + md5);
    }
}
