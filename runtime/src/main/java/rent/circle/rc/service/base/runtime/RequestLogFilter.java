package rent.circle.rc.service.base.runtime;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
@Slf4j
class RequestLogFilter implements ContainerResponseFilter {

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext,
        ContainerResponseContext responseContext) {
        log.info("User: {}", jwt.getName());
        log.info("METHOD: {}", requestContext.getRequest().getMethod());
        log.info("URI: {}", requestContext.getUriInfo().getPath(true));
        log.info("QUERY PARAMS: {}", requestContext.getUriInfo().getQueryParameters());
        log.info("Response Status: {}", responseContext.getStatus());
    }
}