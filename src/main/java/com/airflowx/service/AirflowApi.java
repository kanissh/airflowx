package com.airflowx.service;

import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.DagCollection;
import com.airflowx.dto.dag.DagIsPaused;
import com.airflowx.dto.dag.DagRun;
import com.airflowx.dto.dag.DagRunCollection;
import com.airflowx.dto.dag.DagRunModifyState;
import com.airflowx.dto.error.ImportError;
import com.airflowx.dto.error.ImportErrorCollection;
import com.airflowx.dto.stats.DagStatsCollection;
import com.airflowx.exception.AirflowResponseExceptionMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1")
@RegisterRestClient
@RegisterClientHeaders(value = AirflowClientHeadersFactory.class)
@RegisterProvider(value = AirflowResponseExceptionMapper.class, priority = 50)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AirflowApi {

  @GET
  @Path("/dagStats")
  DagStatsCollection getDagsStatistics(@QueryParam("dag_ids") String commaSepDagIds);

  @PATCH
  @Path("/dags/{dagId}/dagRuns/{dagRunId}")
  DagRun modifyDagRunState(
      @PathParam("dagId") String dagId,
      @PathParam("dagRunId") String dagRunId,
      DagRunModifyState dagRunModifyState);

  @GET
  @Path("/dags/{dagId}/dagRuns")
  DagRunCollection getDagRuns(
      @PathParam("dagId") String dagId,
      @QueryParam("order_by") String orderBy,
      @QueryParam("limit") int limit,
      @QueryParam("state") List<String> stateList);

  @POST
  @Path("/dags/{dagId}/dagRuns")
  DagRun triggerNewDagRun(@PathParam("dagId") String dagId, DagRun dagRun);

  @GET
  @Path("/dags")
  DagCollection getDagList(
      @QueryParam("only_active") Boolean onlyActive,
      @QueryParam("paused") Boolean paused,
      @QueryParam("fields") List<String> fields,
      @QueryParam("dag_id_pattern") String dagIdPatternString);

  @GET
  @Path("/dags/{dagId}")
  Dag getDagInformation(@PathParam("dagId") String dagId);

  @GET
  @Path("/dags/{dagId}/details")
  DagCollection getDetailedDagInformation(@PathParam("dagId") String dagId);

  @GET
  @Path("/importErrors/{import_error_id}")
  ImportError getImportError(@PathParam("import_error_id") int importErrorId);

  @GET
  @Path("/importErrors")
  ImportErrorCollection getAllImportErrors();

  @PATCH
  @Path("/dags")
  DagCollection updateAllDagsPauseState(@QueryParam("dag_id_pattern") String dagIdPattern,
      DagIsPaused dagIsPaused);

  @PATCH
  @Path("/dags/{dag_id}")
  Dag updateDagPauseState(@PathParam("dag_id") String dagId, DagIsPaused dagIsPaused);

}
