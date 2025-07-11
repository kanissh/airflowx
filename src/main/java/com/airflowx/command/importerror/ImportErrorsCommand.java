package com.airflowx.command.importerror;

import com.airflowx.command.HelpMixin;
import com.airflowx.dto.error.ImportError;
import com.airflowx.dto.error.ImportErrorCollection;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import com.airflowx.util.Strings;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "import-errors",
    description = "List DAG files with import errors")
public class ImportErrorsCommand implements Runnable {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Option(names = {"--id"}, description = "Import error id")
  private Integer importErrorId;

  @Inject
  public ImportErrorsCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public ImportErrorsCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public void run() {
    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    if (importErrorId == null) {
      getAllImportErrors(airflowApi);
    } else {
      getImportError(airflowApi, importErrorId);
    }
  }

  private void getImportError(AirflowApi airflowApi, int importErrorId) {
    ImportError importError = airflowApi.getImportError(importErrorId);

    Column[] columns = {
        new Column().header("KEY").dataAlign(HorizontalAlign.LEFT),
        new Column().header("VALUE").dataAlign(HorizontalAlign.LEFT)
    };

    List<String[]> dataList = new ArrayList<>();

    dataList.add(new String[]{"Error id", Strings.getNullSafe(importError.importErrorId())});
    dataList.add(new String[]{"Filename", Strings.getNullSafe(importError.filename())});
    dataList.add(new String[]{"Timestamp", Strings.getNullSafe(importError.timestamp())});
    dataList.add(new String[]{"Stacktrace", Strings.getNullSafe(importError.stackTrace())});

    String table = AsciiTable.builder().border(AsciiTable.NO_BORDERS)
        .data(columns, dataList.toArray(new String[0][]))
        .asString();

    System.out.println();
    System.out.println(table);
    System.out.println();
  }

  private void getAllImportErrors(AirflowApi airflowApi) {
    ImportErrorCollection importErrorCollection = airflowApi.getAllImportErrors();
    String table = AsciiTable.builder()
        .border(AsciiTable.NO_BORDERS)
        .data(
            importErrorCollection.getImportErrorList(),
            List.of(
                new Column().header("ERROR_ID").dataAlign(HorizontalAlign.LEFT)
                    .with(importError -> String.valueOf(importError.importErrorId())),
                new Column().header("FILENAME").dataAlign(HorizontalAlign.LEFT)
                    .with(ImportError::filename),
                new Column().header("TIMESTAMP").dataAlign(HorizontalAlign.LEFT)
                    .with(ImportError::timestamp)
            ))
        .asString();

    System.out.println();
    System.out.println(table);
    System.out.println();
    System.out.println(
        "\sTOTAL NUMBER OF ENTRIES\s\s\s\s" + importErrorCollection.getTotalEntries());
    System.out.println();
  }
}
