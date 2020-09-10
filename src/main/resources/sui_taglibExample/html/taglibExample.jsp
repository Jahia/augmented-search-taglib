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
        <li>${hit.displayableName}</li>
    </c:forEach>
</ol>


<p>Rendering main resource ${renderContext.mainResource.node.path} JSON serialization from Augmented Search</p>
<pre>
<code class="language-json">
<as:toJSON/>
</code>
</pre>

