package org.jahia.modules.augmentedsearch.taglib;

/**
 * Escapes text for HTML element content.
 *
 * <p>A JSP tag that writes to {@code pageContext.getOut()} emits markup, not text. Anything
 * interpolated into that stream is parsed as HTML by the browser, so every value that did not
 * originate in the template must be escaped first. This class does that for the one context the
 * tags in this package write into: HTML element content.
 *
 * <p>The five characters below are the complete set for that context, and the same set
 * {@code fn:escapeXml} handles, so a value escaped here renders identically whether it came
 * through a tag or through the JSP. Values destined for a different context need a different
 * escaping: an attribute, a URL, a {@code <script>} block and a CSS {@code url()} each have
 * their own rules, and reusing this method for them would not be safe.
 *
 * <p>Note that placing untrusted text inside {@code <pre>} or {@code <code>} does NOT make it
 * safe. Those elements preserve whitespace; they do not suppress markup. A value containing
 * {@code </code></pre>} closes the block and everything after it is parsed as ordinary HTML.
 */
final class HtmlText {

    private HtmlText() {
        // utility class
    }

    /**
     * @param value the text to escape; may be {@code null}
     * @return {@code value} with the HTML-significant characters replaced by entities, or an
     *         empty string when {@code value} is {@code null}
     */
    static String escape(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '&':  out.append("&amp;");  break;
                case '<':  out.append("&lt;");   break;
                case '>':  out.append("&gt;");   break;
                case '"':  out.append("&quot;"); break;
                case '\'': out.append("&#39;");  break;
                default:   out.append(c);        break;
            }
        }
        return out.toString();
    }
}
