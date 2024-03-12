/*
 * Copyright (c) 2023-2024 Maveniverse Org.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 */
package eu.maveniverse.maven.toolbox.shared;

/**
 * Set of constants meant to be used in "common builds".
 */
public final class CommonBuilds {
    private CommonBuilds() {}

    public static final ProjectPath PROJECT_PATH_MAIN = new ProjectPath() {
        @Override
        public String getId() {
            return "main";
        }

        @Override
        public int order() {
            return 1;
        }
    };

    public static final ProjectPath PROJECT_PATH_TEST = new ProjectPath() {
        @Override
        public String getId() {
            return "test";
        }

        @Override
        public int order() {
            return 2;
        }
    };

    public static final ProjectPath PROJECT_PATH_IT = new ProjectPath() {
        @Override
        public String getId() {
            return "it";
        }

        @Override
        public int order() {
            return 3;
        }
    };

    public static final BuildPath BUILD_PATH_COMPILE = new BuildPath() {
        @Override
        public String getId() {
            return "compile";
        }

        @Override
        public int order() {
            return 1;
        }
    };

    public static final BuildPath BUILD_PATH_RUNTIME = new BuildPath() {
        @Override
        public String getId() {
            return "runtime";
        }

        @Override
        public int order() {
            return 2;
        }
    };
}
