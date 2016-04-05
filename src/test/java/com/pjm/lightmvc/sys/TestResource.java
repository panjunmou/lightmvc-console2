package com.pjm.lightmvc.sys;

import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.BaseTest;
import com.pjm.lightmvc.dao.sys.ResourceDao;
import com.pjm.lightmvc.model.sys.TResource;
import com.pjm.lightmvc.service.sys.ResourceService;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 */
public class TestResource extends BaseTest {

    @Resource
    private ResourceDao resourceDao;
    @Resource
    private ResourceService resourceService;

    @Test
    public void testTreeList() throws Exception {

    }

    @Test
    public void testQuery() {
        Query query = resourceDao.getEntityManager().createQuery("select p from TResource p where p.parent is null ");
        List resultList = query.getResultList();
        for (int i = 0; i < resultList.size(); i++) {
            TResource resource = (TResource) resultList.get(i);
            System.out.println("resource.getName() = " + resource.getName());
        }
    }

    @Test
    public void testGetAllResource() throws Exception {
        List<TResource> reultList = resourceDao.getPageResult(null, new PageModel()).getReultList();
        for (int i = 0; i < reultList.size(); i++) {
            TResource resource = reultList.get(i);
            System.out.println("resource.getName() = " + resource.getName());
        }
    }

    @Test
    public void testRecursion() {
        TResource rootResource = resourceDao.find(175l);
        List<TResource> resourceList = new ArrayList<>();
        addResource(rootResource, resourceList);
        for (int i = 0; i < resourceList.size(); i++) {
            TResource tResource = resourceList.get(i);
            System.out.println("tResource.getName() = " + tResource.getName());
        }
    }

    private void addResource(TResource tResource,List<TResource> tResourceList) {
        if (tResource != null) {
            for (TResource resource : tResource.getChildren()) {
                tResourceList.add(resource);
                if (resource.getChildren() != null && resource.getChildren().size() > 0) {
                    addResource(resource,tResourceList);
                }
            }
        }
    }
}
