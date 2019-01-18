package ru.vyarus.guicey.gsp.app.filter.redirect;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.guicey.spa.filter.SpaUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * SPA routes support (HTML5 client routing). Support rely on errors handling: when 404 error appear (no matter
 * in assets or views), spa support is checked (if enabled) and spa redirection performed instead of error handling.
 * <p>
 * Error based approach does not influence normal processing: all checks appear only after error appearance
 * (so enabling SPA support will not influence application).
 * <p>
 * Also, SPA support forcefully sets no cache header for index page (or any route, redirected to index page).
 *
 * @author Vyacheslav Rusakov
 * @since 16.01.2019
 */
public class SpaSupport {
    private static final String SPA_ROUTE_POSSIBILITY = "SpaRedirect.SPA_ROUTE_POSSIBILITY";

    private final Logger logger = LoggerFactory.getLogger(SpaSupport.class);

    private final boolean enabled;
    // application root path, but not index page! (assets filter will navigate this route to index page implicitly)
    private final String target;
    private final Pattern noRedirect;

    public SpaSupport(final boolean enabled, final String target, final String noRedirectRegex) {
        this.enabled = enabled;
        this.target = target;
        this.noRedirect = Pattern.compile(noRedirectRegex);
    }

    /**
     * Simple to check for root page: if current request is already root request then no SPA route could be performed.
     * Also, applies no cache header for the root page.
     *
     * @param req request instance
     * @param res response instance
     */
    public void markPossibleSpaRoute(final HttpServletRequest req, final HttpServletResponse res) {
        if (!enabled) {
            return;
        }
        if (SpaUtils.isRootPage(req.getRequestURI(), target)) {
            // index page must be not cacheable
            SpaUtils.noCache(res);
        } else {
            req.setAttribute(SPA_ROUTE_POSSIBILITY, true);
        }
    }

    /**
     * Perform redirection of SPA into index page (so browser receive index html on spa routing url) if conditions
     * match.
     * <ul>
     *     <li>SPA support enabled</li>
     *     <li>It't not already root (see {@link #markPossibleSpaRoute(HttpServletRequest, HttpServletResponse)})</li>
     *     <li>Request accept html response</li>
     *     <li>Request doesn't match filter regexp (describing static files)</li>
     * </ul>
     *
     * @param req request instance
     * @param res response instance
     * @param code error code (http)
     * @return true if redirection performed, false otherwise (SPA route not recognized)
     */
    public boolean redirect(final HttpServletRequest req, final HttpServletResponse res, final int code) {
        if (enabled
                && code == 404
                && req.getAttribute(SPA_ROUTE_POSSIBILITY) != null
                && SpaUtils.isSpaRoute(req, noRedirect)) {
            // redirect to root
            try {
                logger.debug("Perform SPA route redirect for: {}", req.getRequestURI());
                SpaUtils.doRedirect(req, res, target);
            } catch (Exception ex) {
                Throwables.throwIfUnchecked(ex);
                throw new IllegalStateException("Failed to perform SPA redirect", ex);
            }
            return true;
        }
        return false;
    }

}
