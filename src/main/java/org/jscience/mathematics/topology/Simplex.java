/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2025 - Silvere Martin-Michiellot (silvere.martin@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jscience.mathematics.topology;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Simplex (a generalized triangle/tetrahedron).
 * <p>
 * Defined by a set of vertices. This implementation represents an Abstract
 * Simplex
 * where vertices are identified by integers.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class Simplex {
    private final Set<Integer> vertices;

    /**
     * Creates a simplex from a list of vertex IDs.
     * 
     * @param verts vertex IDs
     */
    public Simplex(int... verts) {
        this.vertices = new HashSet<>();
        for (int v : verts)
            vertices.add(v);
    }

    /**
     * Creates a simplex from a set of vertex IDs.
     * 
     * @param verts set of vertex IDs
     */
    public Simplex(Set<Integer> verts) {
        this.vertices = new HashSet<>(verts);
    }

    /**
     * Returns the dimension of this simplex.
     * dim = |vertices| - 1
     * 
     * @return the dimension (0 for point, 1 for line, 2 for triangle, etc.)
     */
    public int dimension() {
        return vertices.size() - 1;
    }

    /**
     * Returns the set of faces of this simplex.
     * A face is formed by removing one vertex.
     * 
     * @return set of face simplices
     */
    public Set<Simplex> faces() {
        Set<Simplex> faces = new HashSet<>();
        for (Integer v : vertices) {
            Set<Integer> faceVerts = new HashSet<>(vertices);
            faceVerts.remove(v);
            if (!faceVerts.isEmpty()) {
                faces.add(new Simplex(faceVerts));
            }
        }
        return faces;
    }

    /**
     * Returns the vertices of this simplex.
     * 
     * @return unmodifiable view of vertices? Or copy?
     */
    public Set<Integer> getVertices() {
        return java.util.Collections.unmodifiableSet(vertices);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Simplex))
            return false;
        Simplex simplex = (Simplex) o;
        return vertices.equals(simplex.vertices);
    }

    @Override
    public int hashCode() {
        return vertices.hashCode();
    }

    @Override
    public String toString() {
        return vertices.toString();
    }
}
