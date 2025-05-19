package com.airflowx.service;

import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.DagRunInfo;
import com.airflowx.dto.dag.DagsInfo;
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
  DagRunInfo getDagRuns(
      @PathParam("dagId") String dagId,
      @QueryParam("order_by") String orderBy,
      @QueryParam("limit") int limit,
      @QueryParam("state") List<String> stateList);

  @GET
  @Path("/dags")
  DagsInfo getDagList(
      @QueryParam("only_active") Boolean onlyActive,
      @QueryParam("paused") Boolean paused,
      @QueryParam("fields") List<String> fields,
      @QueryParam("dag_id_pattern") String dagIdPatternString);

  @GET
  @Path("/dags/{dagId}")
  Dag getDagInformation(@PathParam("dagId") String dagId);

  @GET
  @Path("/dags/{dagId}/details")
  DagsInfo getDetailedDagInformation(@PathParam("dagId") String dagId);

}
