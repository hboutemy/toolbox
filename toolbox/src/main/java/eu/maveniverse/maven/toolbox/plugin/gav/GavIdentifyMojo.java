/*
 * Copyright (c) 2023-2024 Maveniverse Org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 */
package eu.maveniverse.maven.toolbox.plugin.gav;

import eu.maveniverse.maven.toolbox.plugin.GavSearchMojoSupport;
import eu.maveniverse.maven.toolbox.shared.Output;
import eu.maveniverse.maven.toolbox.shared.ToolboxCommando;
import java.io.IOException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import picocli.CommandLine;

/**
 * Identify artifact, either by provided SHA-1 or calculated SHA-1 of a file pointed at.
 */
@CommandLine.Command(
        name = "identify",
        description = "Identify artifact, either by provided SHA-1 or calculated SHA-1 of a file pointed at")
@Mojo(name = "gav-identify", requiresProject = false, threadSafe = true)
public class GavIdentifyMojo extends GavSearchMojoSupport {
    /**
     * Target, a SHA-1 checksum or a file.
     */
    @CommandLine.Parameters(index = "0", description = "Target, a SHA-1 checksum or a file")
    @Parameter(property = "target", required = true)
    private String target;

    @Override
    protected boolean doExecute(Output output, ToolboxCommando toolboxCommando) throws IOException {
        return toolboxCommando.identify(getRemoteRepository(toolboxCommando), target, output);
    }
}
