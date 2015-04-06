package org.apache.ambari.server.api.services;

import com.sun.jersey.core.util.Base64;
import org.apache.ambari.server.api.resources.ResourceInstance;
import org.apache.ambari.server.controller.spi.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * WidgetLayout Service
 */
public class WidgetLayoutService extends BaseService {
  
  private final String clusterName;

  public  WidgetLayoutService(String clusterName) {
    this.clusterName = clusterName;
  }

  @GET
  @Path("{widgetLayoutId}")
  @Produces("text/plain")
  public Response getService(String body, @Context HttpHeaders headers, @Context UriInfo ui,
                             @PathParam("widgetLayoutId") String widgetLayoutId) {

    return handleRequest(headers, body, ui, Request.Type.GET,
            createResource(getUserName(headers), widgetLayoutId));
  }

  /**
   * Handles URL: /widget_layouts
   * Get all instances for a view.
   *
   * @param headers  http headers
   * @param ui       uri info
   *
   * @return instance collection resource representation
   */
  @GET
  @Produces("text/plain")
  public Response getServices(String body, @Context HttpHeaders headers, @Context UriInfo ui) {

    return handleRequest(headers, body, ui, Request.Type.GET,
            createResource(getUserName(headers), null));
  }

  @POST
  @Path("{widgetLayoutId}")
  @Produces("text/plain")
  public Response createService(String body, @Context HttpHeaders headers, @Context UriInfo ui,
                                @PathParam("widgetLayoutId") String widgetLayoutId) {
    return handleRequest(headers, body, ui, Request.Type.POST,
            createResource(getUserName(headers), widgetLayoutId));
  }

  @POST
  @Produces("text/plain")
  public Response createServices(String body, @Context HttpHeaders headers, @Context UriInfo ui) {

    return handleRequest(headers, body, ui, Request.Type.POST,
            createResource(getUserName(headers), null));
  }

  @PUT
  @Path("{widgetLayoutId}")
  @Produces("text/plain")
  public Response updateService(String body, @Context HttpHeaders headers, @Context UriInfo ui,
                                @PathParam("widgetLayoutId") String widgetLayoutId) {

    return handleRequest(headers, body, ui, Request.Type.PUT, createResource(getUserName(headers), widgetLayoutId));
  }

  @PUT
  @Produces("text/plain")
  public Response updateServices(String body, @Context HttpHeaders headers, @Context UriInfo ui) {

    return handleRequest(headers, body, ui, Request.Type.PUT, createResource(getUserName(headers), null));
  }

  @DELETE
  @Path("{widgetLayoutId}")
  @Produces("text/plain")
  public Response deleteService(@Context HttpHeaders headers, @Context UriInfo ui,
                                @PathParam("widgetLayoutId") String widgetLayoutId) {

    return handleRequest(headers, null, ui, Request.Type.DELETE, createResource(getUserName(headers), widgetLayoutId));
  }

  private ResourceInstance createResource(String userName, String widgetLayoutId) {
    Map<Resource.Type,String> mapIds = new HashMap<Resource.Type, String>();
    mapIds.put(Resource.Type.User, userName);
    mapIds.put(Resource.Type.WidgetLayout, widgetLayoutId);
    mapIds.put(Resource.Type.Cluster, clusterName);
    return createResource(Resource.Type.WidgetLayout, mapIds);
  }

  private String getUserName(HttpHeaders headers) {
    String authorizationString = headers.getRequestHeaders().get("Authorization").get(0);
    if (authorizationString != null && authorizationString.startsWith("Basic")) {
      String base64Credentials = authorizationString.substring("Basic".length()).trim();
      String clearCredentials = new String(Base64.decode(base64Credentials),Charset.forName("UTF-8"));
      return clearCredentials.split(":", 2)[0];
    } else {
      return null;
    }
  }
}
