package com.airflowx.command;

import picocli.CommandLine;

public class HelpMixin {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this help message and exit.")
    private boolean help;

}
