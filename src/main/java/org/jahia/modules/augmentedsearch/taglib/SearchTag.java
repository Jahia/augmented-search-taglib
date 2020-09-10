package org.jahia.modules.augmentedsearch.taglib;

import org.jahia.modules.augmentedsearch.graphql.EmptySearchException;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.GqlSearchResults;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.inputs.filters.GqlFilter;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.inputs.filters.GqlFilterNodeType;
import org.jahia.modules.augmentedsearch.service.AugmentedSearchService;
import org.jahia.taglibs.AbstractJahiaTag;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import javax.jcr.RepositoryException;
import javax.servlet.jsp.JspException;
import java.io.IOException;

public class SearchTag extends AbstractJahiaTag {
    private String query;

    @Override
    public int doEndTag() throws JspException {
        BundleContext bundleContext = FrameworkUtil.getBundle(SearchTag.class).getBundleContext();
        if (bundleContext != null) {
            ServiceReference<AugmentedSearchService> searchReference = bundleContext.getServiceReference(AugmentedSearchService.class);
            if (searchReference != null) {
                AugmentedSearchService searchService = bundleContext.getService(searchReference);
                try {
                    GqlSearchResults response = searchService.search(query, AugmentedSearchService.SearchIn.CONTENT, 20, 0,
                                                                     null,
                                                                     new GqlFilter(null, null, null, null,
                                                                                 new GqlFilterNodeType("jnt:content", false),
                                                                                 null, null),
                                                                     getCurrentResource().getNode().getResolveSite().getSiteKey(),
                                                                     getCurrentResource().getLocale().getLanguage(),
                                                                     getCurrentResource().getWorkspace(),
                                                                     getCurrentResource().getNode().getSession());
                    String message = String.format("Search took %s and found %d results when searching for %s",
                                                   response.getTook(),
                                                   response.getTotalHits(),
                                                   query);
                    pageContext.getOut().print(message);
                    pageContext.setAttribute("asResults", response.getHits());
                } catch (EmptySearchException | RepositoryException | IOException e) {
                    throw new JspException(e);
                }
            }
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public void setQ(String q) {
        this.query = q;
    }
}
