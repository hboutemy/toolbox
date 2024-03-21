/*
 * Copyright (c) 2023-2024 Maveniverse Org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 */
package eu.maveniverse.maven.toolbox.plugin;

import eu.maveniverse.maven.mima.context.Context;
import eu.maveniverse.maven.mima.context.ContextOverrides;
import eu.maveniverse.maven.mima.context.Runtime;
import eu.maveniverse.maven.mima.context.Runtimes;
import eu.maveniverse.maven.toolbox.shared.ResolutionScope;
import eu.maveniverse.maven.toolbox.shared.Slf4jOutput;
import eu.maveniverse.maven.toolbox.shared.ToolboxCommando;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mojo(name = "gav-tree", requiresProject = false, threadSafe = true)
public class GavTreeMojo extends AbstractMojo {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The artifact coordinates in the format {@code <groupId>:<artifactId>[:<extension>[:<classifier>]]:<version>}
     * to display tree for.
     */
    @Parameter(property = "gav", required = true)
    private String gav;

    /**
     * The resolution scope to display, accepted values are "main-runtime", "main-compile", "test-runtime" or
     * "test-compile".
     */
    @Parameter(property = "scope", defaultValue = "main-runtime", required = true)
    private String scope;

    /**
     * Set it {@code true} for verbose tree.
     */
    @Parameter(property = "verbose", defaultValue = "false", required = true)
    private boolean verbose;

    /**
     * Apply BOMs, if needed.
     */
    @Parameter(property = "boms", defaultValue = "")
    private java.util.List<String> boms;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Runtime runtime = Runtimes.INSTANCE.getRuntime();
        try (Context context = runtime.create(ContextOverrides.create().build())) {
            ToolboxCommando toolboxCommando = ToolboxCommando.getOrCreate(runtime, context);
            toolboxCommando.tree(
                    ResolutionScope.parse(scope), toolboxCommando.loadGav(gav, boms), false, new Slf4jOutput(logger));
        } catch (RuntimeException e) {
            throw new MojoExecutionException(e);
        } catch (ArtifactDescriptorException e) {
            throw new MojoFailureException(e);
        }
    }
}
