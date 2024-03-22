/*
 * Copyright (c) 2023-2024 Maveniverse Org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 */
package eu.maveniverse.maven.toolbox.plugin.mp;

import eu.maveniverse.maven.toolbox.plugin.MPMojoSupport;
import eu.maveniverse.maven.toolbox.shared.ResolutionScope;
import eu.maveniverse.maven.toolbox.shared.ToolboxCommando;
import java.util.Collections;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Resolves transitively given project.
 */
@Mojo(name = "resolve-transitive", requiresProject = false, threadSafe = true)
public class ResolveTransitiveMojo extends MPMojoSupport {
    /**
     * The resolution scope to resolve, accepted values are "runtime", "compile", "test", etc.
     */
    @Parameter(property = "scope", defaultValue = "runtime", required = true)
    private String scope;

    @Parameter(property = "sources", defaultValue = "false")
    private boolean sources;

    @Parameter(property = "javadoc", defaultValue = "false")
    private boolean javadoc;

    @Parameter(property = "signature", defaultValue = "false")
    private boolean signature;

    @Override
    protected void doExecute(ToolboxCommando toolboxCommando) throws Exception {
        toolboxCommando.resolveTransitive(
                ResolutionScope.parse(scope),
                Collections.singleton(projectAsResolutionRoot()),
                sources,
                javadoc,
                signature,
                output);
    }
}
