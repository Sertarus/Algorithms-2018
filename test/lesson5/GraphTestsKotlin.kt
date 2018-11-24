package lesson5

import lesson5.impl.GraphBuilder
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import kotlin.test.assertEquals

class GraphTestsKotlin : AbstractGraphTests() {

    @Test
    @Tag("Normal")
    fun testFindEulerLoop() {
        findEulerLoop { findEulerLoop() }
    }

    @Test
    @Tag("Normal")
    fun testMinimumSpanningTree() {
        minimumSpanningTree { minimumSpanningTree() }
    }

    @Test
    @Tag("Hard")
    fun testLargestIndependentVertexSet() {
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            addConnection(a, b)
            addConnection(a, c)
            addConnection(b, d)
            addConnection(c, e)
            addConnection(c, f)
            addConnection(b, g)
            addConnection(d, i)
            addConnection(g, h)
            addConnection(h, j)
        }.build()
        val independent = graph.largestIndependentVertexSet()
        assertEquals(setOf(graph["A"], graph["D"], graph["E"], graph["F"], graph["G"], graph["J"]), independent)
        val graph2 = GraphBuilder().build()
        val independent2 = graph2.largestIndependentVertexSet()
        assertEquals(setOf(), independent2)
        val graph3 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            val k = addVertex("K")
            val l = addVertex("L")
            val m = addVertex("M")
            val n = addVertex("N")
            val o = addVertex("O")
            val p = addVertex("P")
            val q = addVertex("Q")
            val r = addVertex("R")
            val s = addVertex("S")
            val t = addVertex("T")
            addConnection(a, b)
            addConnection(a, c)
            addConnection(b, d)
            addConnection(c, e)
            addConnection(c, f)
            addConnection(b, g)
            addConnection(d, i)
            addConnection(g, h)
            addConnection(h, j)
            addConnection(k, l)
            addConnection(k, m)
            addConnection(l, n)
            addConnection(m, o)
            addConnection(m, p)
            addConnection(l, q)
            addConnection(n, s)
            addConnection(q, r)
            addConnection(r, t)
            addConnection(j, t)
        }.build()
        val independent3 = graph3.largestIndependentVertexSet()
        assertEquals(setOf(graph3["A"], graph3["D"], graph3["E"], graph3["F"], graph3["G"], graph3["K"], graph3["N"],
                graph3["O"], graph3["P"], graph3["Q"], graph3["J"]), independent3)
    }

    @Test
    @Tag("Hard")
    fun testLongestSimplePath() {
        val graph = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(a, c)
        }.build()
        val longestPath = graph.longestSimplePath()
        assertEquals(2, longestPath.length)
        val graph2 = GraphBuilder().apply {
            val a = addVertex("A")
            val b = addVertex("B")
            val c = addVertex("C")
            val d = addVertex("D")
            val e = addVertex("E")
            val f = addVertex("F")
            val g = addVertex("G")
            val h = addVertex("H")
            val i = addVertex("I")
            val j = addVertex("J")
            val k = addVertex("K")
            addConnection(a, b)
            addConnection(b, c)
            addConnection(c, d)
            addConnection(a, e)
            addConnection(d, k)
            addConnection(e, j)
            addConnection(j, k)
            addConnection(b, f)
            addConnection(c, i)
            addConnection(f, i)
            addConnection(b, g)
            addConnection(g, h)
            addConnection(h, c)
        }.build()
        val longestPath2 = graph2.longestSimplePath()
        assertEquals(10, longestPath2.length)
        val graph3 = GraphBuilder().apply {
            addVertex("A")
        }.build()
        assertEquals(0, graph3.longestSimplePath().length)
    }
}