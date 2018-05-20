package lt.mif.ise.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MethodLogger extends HandlerInterceptorAdapter{

    private static final Logger logger = LoggerFactory.getLogger(MethodLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();

        logger.info("\n\n-------- Logger.preHandle --- " +
                    "\nRequest URL: " + request.getRequestURL() +
                    "\nStart Time: " + System.currentTimeMillis() +
                ((null != request.getUserPrincipal()) ?
                    "\nPrincipal: " + request.getUserPrincipal() : "") +
                    "\nMethod: " + request.getMethod());

        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
                                Object handler, Exception ex) throws Exception {
        logger.info("\n\n-------- Logger.postHandle --- " +
                    "\nRequest URL: " + request.getRequestURL() +
                    "\nSTATUS: " + response.getStatus() +
                    ((null != ex) ? "\nException : "+ ex : ""));

    }

}