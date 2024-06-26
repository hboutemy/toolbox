/*
 * Copyright (c) 2023-2024 Maveniverse Org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 */
package eu.maveniverse.maven.toolbox.shared;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.Dependency;

/**
 * Resolution root, that directs what we want to resolve (or collect).
 * <p>
 * To circumvent Resolver 1.x issue manifested with wrong Runtime graph building.
 */
public final class ResolutionRoot {
    private final Artifact artifact;
    private final boolean load;
    private final boolean prepared;
    private final List<Dependency> dependencies;
    private final List<Dependency> managedDependencies;

    private ResolutionRoot(
            Artifact artifact,
            boolean load,
            boolean prepared,
            List<Dependency> dependencies,
            List<Dependency> managedDependencies) {
        this.artifact = artifact;
        this.load = load;
        this.prepared = prepared;
        this.dependencies = dependencies;
        this.managedDependencies = managedDependencies;
    }

    /**
     * The artifact to resolve.
     */
    public Artifact getArtifact() {
        return artifact;
    }

    /**
     * Should its POM be loaded?
     */
    public boolean isLoad() {
        return load;
    }

    /**
     * Is this instance prepared (for processing)?
     */
    public boolean isPrepared() {
        return prepared;
    }

    /**
     * To mark root as prepared (for processing).
     * <p>
     * Note: users should not invoke this method, or at least, should be aware of the consequences.
     */
    public ResolutionRoot prepared() {
        return new ResolutionRoot(
                artifact,
                load,
                true,
                dependencies == null ? Collections.emptyList() : dependencies,
                managedDependencies == null ? Collections.emptyList() : managedDependencies);
    }

    /**
     * Explicitly specified direct dependencies.
     * <p>
     * If {@link #isLoad()} is {@code true}, these dependencies will be merged with loaded POM dependencies as
     * "dominant" ones. Otherwise, only these dependencies will be considered.
     * <p>
     * Returns {@code null} if not specified and {@link #isLoad()} is {@code true}.
     */
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    /**
     * Explicitly specified dependency management.
     * <p>
     * If {@link #isLoad()} is {@code true}, these dependency management entries will be merged with loaded POM
     * dependency management as "dominant" ones. Otherwise, only these dependency management entries will be considered.
     * <p>
     * Returns {@code null} if not specified.
     */
    public List<Dependency> getManagedDependencies() {
        return managedDependencies;
    }

    /**
     * Returns builder that treats artifact as existing, and will load up its POM to fill in details.
     */
    public static Builder ofLoaded(Artifact artifact) {
        return new Builder(artifact).load();
    }

    /**
     * Returns builder that treats artifact as not existing, and will not load up its POM. Returned builder is
     * incomplete, and must have further details filled in, like {@link Builder#withDependencies(List)}.
     */
    public static Builder ofNotLoaded(Artifact artifact) {
        return new Builder(artifact).doNotLoad();
    }

    public static final class Builder {
        private final Artifact artifact;
        private boolean load = false;
        private List<Dependency> dependencies = null;
        private List<Dependency> managedDependencies = null;

        private Builder(Artifact artifact) {
            this.artifact = requireNonNull(artifact, "artifact");
        }

        public Builder load() {
            this.load = true;
            return this;
        }

        public Builder doNotLoad() {
            this.load = false;
            return this;
        }

        public Builder withDependencies(List<Dependency> dependencies) {
            this.dependencies = new ArrayList<>(dependencies);
            return this;
        }

        public Builder withManagedDependencies(List<Dependency> managedDependencies) {
            this.managedDependencies = new ArrayList<>(managedDependencies);
            return this;
        }

        public ResolutionRoot build() {
            if (!load && dependencies == null) {
                throw new IllegalStateException("must specify dependencies for non-loaded artifacts");
            }
            return new ResolutionRoot(artifact, load, false, dependencies, managedDependencies);
        }
    }
}
