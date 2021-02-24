package org.jahia.modules.augmentedsearch.taglib;

import org.jahia.modules.augmentedsearch.service.AugmentedSearchService;
import org.jahia.taglibs.AbstractJahiaTag;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import javax.jcr.RepositoryException;
import javax.servlet.jsp.JspException;
import java.io.IOException;

public class JSONTag extends AbstractJahiaTag {

    @Override
    public int doEndTag() throws JspException {
        BundleContext bundleContext = FrameworkUtil.getBundle(SearchTag.class).getBundleContext();
        if (bundleContext != null) {
            ServiceReference<AugmentedSearchService> searchReference = bundleContext.getServiceReference(AugmentedSearchService.class);
            if (searchReference != null) {
                AugmentedSearchService searchService = bundleContext.getService(searchReference);
                try {
                    pageContext.getOut().print(searchService.convertToJSONForIndexation(getRenderContext().getMainResource().getNode()));
                } catch (IOException | RepositoryException e) {
                    throw new JspException(e);
                }
            }
        }
        return SKIP_BODY;
    }
}
