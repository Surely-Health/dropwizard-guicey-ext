package ru.vyarus.guicey.gsp.app.util;

import com.google.common.base.CharMatcher;
import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.guicey.gsp.views.template.TemplateNotFoundException;

import java.util.Iterator;
import java.util.List;

/**
 * Utility used to lookup static resources in multiple locations. This is used for applications extensions
 * mechanism when additional resources could be mapped into application from different classpath location.
 *
 * @author Vyacheslav Rusakov
 * @since 04.12.2018
 */
public final class ResourceLookup {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLookup.class);

    private ResourceLookup() {
    }

    /**
     * Searches provided resource in multiple classpath locations.
     *
     * @param path      static resource path
     * @param rootPaths classpath folders to search resource in
     * @return resource location path (first occurrence) or null if not found
     */
    public static String lookup(final String path, final List<String> rootPaths) {
        final ClassLoader loader =
                MoreObjects.firstNonNull(
                        Thread.currentThread().getContextClassLoader(), ResourceLookup.class.getClassLoader());
        final String templatePath = CharMatcher.is('/').trimLeadingFrom(path);
        final Iterator<String> it = rootPaths.iterator();
        String location;
        String res = null;
        while (res == null && it.hasNext()) {
            location = it.next();
            if (loader.getResource(location + templatePath) != null) {
                res = location + templatePath;
            }
        }
        return res;
    }

    /**
     * Shortcut for {@link #lookup(String, List)} with fail in case of not found template.
     *
     * @param path      static resource path
     * @param rootPaths classpath folders to search resource in
     * @return resource location path (first occurrence)
     * @throws TemplateNotFoundException if template not found
     */
    public static String lookupOrFail(final String path, final List<String> rootPaths)
            throws TemplateNotFoundException {
        final String lookup = ResourceLookup.lookup(path, rootPaths);
        if (lookup == null) {
            final String err = String.format("Template '%s' not found in locations: %s", path, rootPaths);
            // logged here because exception most likely will be handled as 404 response
            LOGGER.error(err);
            throw new TemplateNotFoundException(err);
        }
        return lookup;
    }
}
