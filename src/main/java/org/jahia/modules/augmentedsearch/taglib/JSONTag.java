package org.jahia.modules.augmentedsearch.taglib;

import org.jahia.modules.augmentedsearch.service.AugmentedSearchService;
import org.jahia.taglibs.AbstractJahiaTag;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import java.io.IOException;

public class JSONTag extends AbstractJahiaTag {
    private static Logger logger = LoggerFactory.getLogger(JSONTag.class);
    @Override
    public int doEndTag() throws JspException {
        BundleContext bundleContext = FrameworkUtil.getBundle(SearchTag.class).getBundleContext();
        if (bundleContext != null) {
            ServiceReference<AugmentedSearchService> searchReference = bundleContext.getServiceReference(AugmentedSearchService.class);
            if (searchReference != null) {
                AugmentedSearchService searchService = bundleContext.getService(searchReference);
                try {
                    // The serialized JSON carries node property VALUES, which are author-
                    // controlled and therefore untrusted. It is written into HTML element
                    // content, so it is escaped here. The <pre><code> wrapper in the view
                    // preserves whitespace but does not suppress markup, so it is not a
                    // substitute for this call.
                    pageContext.getOut().print(HtmlText.escape(
                            searchService.convertToJSONForIndexation(getRenderContext().getMainResource().getNode())));
                } catch (IOException | RepositoryException e) {
                    logger.error(e.getMessage(), e);
                    throw new JspException(e);
                }
            }
        }
        return SKIP_BODY;
    }
}
