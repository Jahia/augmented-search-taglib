<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="as" uri="http://www.jahia.org/augmented-search/tags" %>
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>
<%--@elvariable id="asResults" type="java.util.List<org.jahia.modules.augmentedsearch.graphql.extensions.models.GqlSearchHit>"--%>
<template:addResources type="css" resources="css/prism.css"/>
<template:addResources type="javascript" resources="javascript/prism.js"/>
<c:if test="${renderContext.editMode}">
    <legend>${fn:escapeXml(jcr:label(currentNode.primaryNodeType, currentResource.locale))}</legend>
</c:if>

<as:search q="ceo"/>
<ol>
    <c:forEach items="${asResults}" var="hit">
        <%-- Search hits carry author-controlled content, so the name is escaped for the
             HTML element content it is written into. --%>
        <li>${fn:escapeXml(hit.displayableName)}</li>
    </c:forEach>
</ol>


<%-- A JCR node name may contain characters that are significant in HTML, so the path is
     escaped like every other value that did not originate in this template. --%>
<p>Rendering main resource ${fn:escapeXml(renderContext.mainResource.node.path)} JSON serialization from Augmented Search</p>
<%-- <pre> and <code> preserve whitespace; they do NOT suppress markup. The escaping that
     makes this block safe is done by the tag itself, not by these elements. --%>
<pre>
<code class="language-json">
<as:toJSON/>
</code>
</pre>

