package org.jscience.mathematics.topology;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

/**
 * Represents a Simplicial Complex.
 * <p>
 * A simplicial complex is a set of simplices that satisfies the following
 * conditions:
 * 1. Every face of a simplex from the set is also in the set.
 * 2. The non-empty intersection of any two simplices σ1, σ2 is a face of both
 * σ1 and σ2.
 * </p>
 * 
 * @author Silvere Martin-Michiellot
 * @author Gemini AI (Google DeepMind)
 * @since 1.0
 */
public class SimplicialComplex {

    private final Set<Simplex> simplices;

    public SimplicialComplex() {
        this.simplices = new HashSet<>();
    }

    /**
     * Adds a simplex and all its faces to the complex.
     * 
     * @param vertices the vertices defining the simplex
     */
    public void addSimplex(int... vertices) {
        Simplex s = new Simplex(vertices);
        addSimplexRecursive(s);
    }

    private void addSimplexRecursive(Simplex s) {
        if (simplices.contains(s))
            return;
        simplices.add(s);

        // Add all faces (sub-simplices)
        if (s.dimension() > 0) {
            for (Simplex face : s.faces()) {
                addSimplexRecursive(face);
            }
        }
    }

    public int dimension() {
        return simplices.stream().mapToInt(Simplex::dimension).max().orElse(-1);
    }

    public int size() {
        return simplices.size();
    }

    public Set<Simplex> getSimplices() {
        return Collections.unmodifiableSet(simplices);
    }

    /**
     * Represents a Simplex (a generalized triangle/tetrahedron).
     * Defined by a set of vertices.
     */
    public static class Simplex {
        private final Set<Integer> vertices;

        public Simplex(int... verts) {
            this.vertices = new HashSet<>();
            for (int v : verts)
                vertices.add(v);
        }

        public Simplex(Set<Integer> verts) {
            this.vertices = new HashSet<>(verts);
        }

        public int dimension() {
            return vertices.size() - 1;
        }

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
}
