package lt.mif.ise.logging;

import org.apache.catalina.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MethodLogger extends HandlerInterceptorAdapter{

    private static final Logger logger = LoggerFactory.getLogger(MethodLogger.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        long startTime = System.currentTimeMillis();

        try{
            logger.info("\n\n-------- Logger.preHandle --- " +
                    "\nRequest URL: " + request.getRequestURL() +
                    "\nStart Time: " + System.currentTimeMillis() +
                    ((null != request.getUserPrincipal()) ?
                            "\nPrincipal: " + request.getUserPrincipal().getName() +
                                    "\nAuthorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString() : "") +
                    "\nMethod: " + request.getMethod());

            request.setAttribute("startTime", startTime);

        }
        catch(Exception e){
            logger.info(e.getMessage());
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
                           Object handler, ModelAndView modelAndView){

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
                                Object handler, Exception ex) {
        try{
            logger.info("\n\n-------- Logger.postHandle --- " +
                    "\nRequest URL: " + request.getRequestURL() +
                    "\nSTATUS: " + response.getStatus() +
                    ((null != ex) ? "\nException : "+ ex : ""));
        }
        catch(Exception e){ logger.info(e.getMessage()); }
    }
}