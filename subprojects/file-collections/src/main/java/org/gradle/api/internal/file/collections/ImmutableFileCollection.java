/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.file.collections;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.gradle.api.internal.file.AbstractFileCollection;

import java.io.File;
import java.util.Set;

public abstract class ImmutableFileCollection extends AbstractFileCollection {
    private static final ImmutableFileCollection EMPTY = new ImmutableFileCollection() {
        @Override
        public Set<File> getFiles() {
            return ImmutableSet.of();
        }
    };

    /**
     * Please use {@link org.gradle.api.file.ProjectLayout#files(Object...)} (for plugins) or {@link org.gradle.api.internal.file.FileCollectionFactory} (for core code) instead.
     * This method will be removed eventually.
     */
    public static ImmutableFileCollection of() {
        return EMPTY;
    }

    /**
     * Please use {@link org.gradle.api.file.ProjectLayout#files(Object...)} (for plugins) or {@link org.gradle.api.internal.file.FileCollectionFactory} (for core code) instead.
     * This method will be removed eventually.
     */
    public static ImmutableFileCollection of(File... files) {
        if (files.length == 0) {
            return EMPTY;
        }
        return new FileOnlyImmutableFileCollection(ImmutableSet.copyOf(files));
    }

    /**
     * Please use {@link org.gradle.api.file.ProjectLayout#files(Object...)} (for plugins) or {@link org.gradle.api.internal.file.FileCollectionFactory} (for core code) instead.
     * This method will be removed eventually.
     */
    public static ImmutableFileCollection of(Iterable<? extends File> files) {
        if (Iterables.isEmpty(files)) {
            return EMPTY;
        }
        return new FileOnlyImmutableFileCollection(ImmutableSet.copyOf(files));
    }

    private ImmutableFileCollection() {
    }

    @Override
    public String getDisplayName() {
        return "file collection";
    }

    private static class FileOnlyImmutableFileCollection extends ImmutableFileCollection {
        private final ImmutableSet<File> files;

        FileOnlyImmutableFileCollection(ImmutableSet<File> files) {
            this.files = files;
        }

        @Override
        public Set<File> getFiles() {
            return files;
        }


        @Override
        public String toString() {
            if (files.size() == 1) {
                return String.format("file '%s'", files.iterator().next().getAbsolutePath());
            }

            return super.toString();
        }
    }
}
