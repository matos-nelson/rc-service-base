package rent.circle.rc.service.base.runtime;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Provider
@Slf4j
class RequestLogFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    JsonWebToken jwt;

    private String buildLogMessage(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nUser: ").append(jwt.getName()).append(" made request. \n");
        sb.append("Method: ").append(requestContext.getRequest().getMethod()).append("\n");
        sb.append("URI: ").append(requestContext.getUriInfo().getPath(true)).append("\n");
        sb.append("QUERY PARAMS: ").append(requestContext.getUriInfo().getQueryParameters()).append("\n");

        if(responseContext != null) {
            sb.append("Response Status: ").append(responseContext.getStatus());
        }else {
            sb.append("Request Received.\n");
        }

        sb.setLength(500);
        return sb.toString();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        log.info(buildLogMessage(requestContext, null));
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
        ContainerResponseContext responseContext) {
        log.info(buildLogMessage(requestContext, responseContext));
   }
}