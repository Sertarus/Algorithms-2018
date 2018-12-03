@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson5

import java.util.*

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */
fun Graph.minimumSpanningTree(): Graph {
    TODO()
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
// T = O(V + E) R = O(V), где V - количество вершин в графе E - количество рёбер
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    val verticesAndDegrees = sortedMapOf<Int, MutableSet<Graph.Vertex>>()
    this.vertices.forEach {
        verticesAndDegrees.getOrPut(this.getConnections(it).size) { mutableSetOf() }.add(it) //V
    }
    val resultForHighestDegreeSearch = mutableSetOf<Graph.Vertex>()
    val prohibitedVerticesForHighestDegreeSearch = mutableSetOf<Graph.Vertex>()
    val pointsToCheckForHighestDegreeSearch = sortedMapOf<Int, SortedMap<Int, Graph.Vertex>>()
    val resultForLeastDegreeSearch = mutableSetOf<Graph.Vertex>()
    val prohibitedVerticesForLeastDegreeSearch = mutableSetOf<Graph.Vertex>()
    val pointsToCheckForLeastDegreeSearch = sortedMapOf<Int, SortedMap<Int, Graph.Vertex>>()
    verticesAndDegrees.entries.reversed().forEach { entry -> //V
        entry.value.forEach { vertex ->
            val index = this.vertices.indexOf(vertex)
            pointsToCheckForLeastDegreeSearch.getOrPut(entry.key) { sortedMapOf() }[index] = vertex
            this.getNeighbors(vertex).forEach { neighbour -> //0,n * E, где n - какое-то число
                val neighbourIndex = this.vertices.indexOf(neighbour)
                pointsToCheckForHighestDegreeSearch.getOrPut(entry.key) { sortedMapOf() }[neighbourIndex] = neighbour
            }
        }
    }
    var indexSumForHighestDegreeSearch = 0
    pointsToCheckForHighestDegreeSearch.values.reversed().forEach { mapOfMaps -> //0,n * V
        mapOfMaps.forEach { mapOfVertices ->
            if (!prohibitedVerticesForHighestDegreeSearch.contains(mapOfVertices.value)
                    && !resultForHighestDegreeSearch.contains(mapOfVertices.value)) {
                this.getNeighbors(mapOfVertices.value).forEach { //0,n * E
                    prohibitedVerticesForHighestDegreeSearch.add(it)
                }
                resultForHighestDegreeSearch.add(mapOfVertices.value)
                indexSumForHighestDegreeSearch += mapOfVertices.key
            }
        }
    }
    var indexSumForLeastDegreeSearch = 0
    pointsToCheckForLeastDegreeSearch.values.forEach { mapOfMaps -> //0,n * V
        mapOfMaps.forEach { mapOfVertices ->
            if (!prohibitedVerticesForLeastDegreeSearch.contains(mapOfVertices.value)
                    && !resultForLeastDegreeSearch.contains(mapOfVertices.value)) {
                this.getNeighbors(mapOfVertices.value).forEach { //0,n * E
                    prohibitedVerticesForLeastDegreeSearch.add(it)
                }
                resultForLeastDegreeSearch.add(mapOfVertices.value)
                indexSumForLeastDegreeSearch += mapOfVertices.key
            }
        }
    }
    return when {
        resultForHighestDegreeSearch.size > resultForLeastDegreeSearch.size -> resultForHighestDegreeSearch
        resultForHighestDegreeSearch.size == resultForLeastDegreeSearch.size ->
            return if (indexSumForHighestDegreeSearch < indexSumForLeastDegreeSearch) resultForHighestDegreeSearch
            else resultForLeastDegreeSearch
        else -> resultForLeastDegreeSearch
    }
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
// T = O(L!*2^L*V) R = O(V), где L - длина самого длинного пути
fun Graph.longestSimplePath(): Path {
    val bestPath = mutableListOf<Graph.Vertex>()
    this.vertices.forEach { vertex ->
        var currentPath = mutableListOf<Graph.Vertex>(vertex)
        val prohibitedTransitions = mutableListOf<Pair<Graph.Vertex, Graph.Vertex>>()
        while (true) {
            var transitionCompleted = false
            this.getNeighbors(currentPath.last()).forEach {
                val transition = Pair(currentPath.last(), it)
                if (!transitionCompleted && !currentPath.contains(it) && !prohibitedTransitions.contains(transition)) {
                    currentPath.add(it)
                    transitionCompleted = true
                }
            }
            if (!transitionCompleted) {
                var lastForkIndex = 0
                if (currentPath.size > bestPath.size) {
                    bestPath.clear()
                    bestPath.addAll(currentPath)
                }
                if (currentPath.size == 1) break
                if (currentPath.size > 1) {
                    for (i in currentPath.size - 2 downTo 0) {
                        if (this.getNeighbors(currentPath[i]).size > 2) {
                            var transitionIsProhibited = false
                            this.getNeighbors(currentPath[i]).forEach {
                                val sublistContainsIt = currentPath.subList(0, currentPath.size - i - 1).contains(it)
                                if (!transitionIsProhibited && !sublistContainsIt) {
                                    prohibitedTransitions.add(Pair(currentPath[i], currentPath[i + 1]))
                                    transitionIsProhibited = true
                                    lastForkIndex = i
                                }
                            }
                        }
                        if (i == 0) {
                            prohibitedTransitions.add(Pair(currentPath[0], currentPath[1]))
                        }
                    }
                }
                currentPath = currentPath.subList(0, lastForkIndex + 1)
            }
        }
    }
    var resultPath = Path(bestPath.first())
    for (i in 1 until bestPath.size) resultPath = Path(resultPath, this, bestPath[i])
    return resultPath
}