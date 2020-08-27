package org.jahia.modules.augmentedsearch.taglib;

import org.elasticsearch.action.search.SearchResponse;
import org.jahia.api.Constants;
import org.jahia.modules.augmentedsearch.ESNotConnectedException;
import org.jahia.modules.augmentedsearch.graphql.EmptySearchException;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.GqlSearchResponse;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.inputs.GqlSearchInput;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.inputs.filters.GqlFilter;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.inputs.filters.GqlFilterNodeType;
import org.jahia.modules.augmentedsearch.graphql.extensions.models.inputs.sort.GqlSort;
import org.jahia.modules.augmentedsearch.service.AugmentedSearchService;
import org.jahia.modules.graphql.provider.dxm.node.NodeQueryExtensions;
import org.jahia.taglibs.AbstractJahiaTag;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Arrays;

public class SearchTag extends AbstractJahiaTag {

    private static Logger logger = LoggerFactory.getLogger(SearchTag.class);
    private String query;

    @Override
    public int doEndTag() throws JspException {
        BundleContext bundleContext = FrameworkUtil.getBundle(SearchTag.class).getBundleContext();
        if (bundleContext != null) {
            ServiceReference<AugmentedSearchService> searchReference = bundleContext.getServiceReference(AugmentedSearchService.class);
            if (searchReference != null) {
                AugmentedSearchService searchService = bundleContext.getService(searchReference);
                NodeQueryExtensions.Workspace workspace = getCurrentResource().getWorkspace().equals(Constants.EDIT_WORKSPACE) ? NodeQueryExtensions.Workspace.EDIT : NodeQueryExtensions.Workspace.LIVE;
                try {
                    SearchResponse response = searchService.search(new GqlSearchInput(query, Arrays.asList(GqlSearchInput.SearchIn.CONTENT), 20, 0),
                                                                   null,
                                                                   new GqlFilter(null, null, null, null,
                                                                                 new GqlFilterNodeType("jnt:content", false),
                                                                                 null, null),
                                                                   getCurrentResource().getNode().getResolveSite().getSiteKey(),
                                                                   getCurrentResource().getLocale().getLanguage(),
                                                                   workspace,
                                                                   null,
                                                                   getUser()).getMainQueryResponse();
                    String message = String.format("Search took %s and found %d results when searching for %s",
                                                   response.getTook().toHumanReadableString(2),
                                                   response.getHits().getTotalHits().value,
                                                   query);
                    pageContext.getOut().print(message);
                    pageContext.setAttribute("asResults",response.getHits());
                } catch (ESNotConnectedException | EmptySearchException | InterruptedException | RepositoryException | IOException e) {
                    logger.error(e.getMessage(), e);
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
