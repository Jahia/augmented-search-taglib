## Augmented Search Taglib Example

This module shows an example of taglib using Augmented Search OSGI Service

There are two tags

To use them insert in your JSP

`<%@ taglib prefix="as" uri="http://www.jahia.org/augmented-search/tags" %>`

Example of usage:

```
<as:search q="ceo"/>
 <ol>
     <c:forEach items="${asResults.hits}" var="hit">
         <li>${hit.fields["jgql:displayableName"].value}</li>
     </c:forEach>
 </ol>
 <p>Rendering main resource ${renderContext.mainResource.node.path} JSON serialization from Augmented Search</p>
 <pre>
 <code class="language-json">
 <as:toJSON/>
 </code>
 </pre>
```
Voila !