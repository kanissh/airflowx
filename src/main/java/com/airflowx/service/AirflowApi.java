package com.airflowx.service;

import com.airflowx.dto.DagsInfo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1")
@RegisterRestClient
@RegisterClientHeaders(value = AirflowClientHeadersFactory.class)
public interface AirflowApi {

  @GET
  @Path("/dags/{dagId}/dagRuns")
  DagsInfo getDagRuns(@PathParam("dagId") String dagId);

  @GET
  @Path("/dags")
  DagsInfo getDagList(
      @QueryParam("only_active") Boolean onlyActive,
      @QueryParam("paused") Boolean paused,
      @QueryParam("fields") List<String> fields);
}
