package com.anecon.taf.client.white;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface IRemoteUIClientAnnotated extends IRemoteUIClient {
    @POST
    @Path("/application/")
    @Override
    void startApplication(ApplicationConfiguration config);

    @DELETE
    @Path("/application/")
    @Override
    void stopApplication();

    @PUT
    @Path("/application/")
    @Override
    void stopApplication(ApplicationConfiguration config);

    @GET
    @Path("/application/")
    @Override
    ApplicationConfiguration getRunningApplication();

    @POST
    @Path("/elements/click")
    @Override
    void click(SearchProperties searchProperties);

    @POST
    @Path("/elements/click")
    @Override
    void click(SearchProperties searchProperties, int times);

    @POST
    @Path("/elements/type/{inputText}")
    @Override
    void setText(SearchProperties searchProperties, @PathParam("inputText") String text);

    @POST
    @Path("/elements/type/{inputText}")
    @Override
    void setText(SearchProperties searchProperties, @PathParam("inputText") String text, int times);

    @POST
    @Path("/elements/focus")
    @Override
    void focus(SearchProperties searchProperties);

    @POST
    @Path("/elements/text")
    @Override
    String getText(SearchProperties searchProperties);

    @POST
    @Path("/elements/selectRows/{cellContent}")
    @Override
    int selectRows(SearchProperties searchProperties, @PathParam("cellContent") String cellContent);

    @POST
    @Path("/elements/visible")
    @Override
    boolean isVisible(SearchProperties searchProperties);

    @POST
    @Path("/elements/enabled")
    @Override
    boolean isEnabled(SearchProperties searchProperties);

    @POST
    @Path("/elements/checked")
    @Override
    boolean isChecked(SearchProperties searchProperties);

    @POST
    @Path("/elements/takeScreenshot")
    @Override
    String takeScreenshot(SearchProperties searchProperties);

    @GET
    @Path("/elements/clipboard")
    @Override
    String getClipboardContent();

    @POST
    @Path("/elements/clipboard")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // Not JSON because C# WebAPI uses [FromBody] attribute -> only works with Content-Type=FORM_URLENCODED
    void setClipboardContent(String newContent);

}

