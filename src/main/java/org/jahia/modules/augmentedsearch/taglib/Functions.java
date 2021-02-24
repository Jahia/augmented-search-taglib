package org.jahia.modules.augmentedsearch.taglib;

import org.jahia.modules.augmentedsearch.graphql.extensions.models.GqlSearchHitV2;


public class Functions {
    private Functions() {
    }

    public static String getProperty(GqlSearchHitV2 hit, String propertyName) {
        return hit.getPropertyValue(propertyName);
    }
}
