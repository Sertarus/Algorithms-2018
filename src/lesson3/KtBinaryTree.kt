package lesson3

import java.util.*
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    override fun add(element: T): Boolean {
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> root = newNode
            comparison < 0 -> {
                assert(closest.left == null)
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                closest.right = newNode
            }
        }
        size++
        return true
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        TODO()
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null

        /**
         * Поиск следующего элемента
         * Средняя
         */
        // T = O(h), R = O(h), где h - высота дерева
        private fun findNext(): Node<T>? {
            val oldValue = current?.value ?: return find(first())
            if (oldValue == last()) return null
            var tempCurrent = root
            val wayList = mutableListOf<Node<T>?>()
            val checkedNodes = mutableSetOf<Node<T>?>()
            while (true) {
                val leftNodeFits = tempCurrent?.left != null && tempCurrent.value > oldValue
                val rightNodeFits = tempCurrent?.right != null && tempCurrent.value <= oldValue
                when {
                    (leftNodeFits && !checkedNodes.contains(tempCurrent?.left)) -> {
                        wayList.add(tempCurrent)
                        tempCurrent = tempCurrent?.left
                    }
                    (rightNodeFits && !checkedNodes.contains(tempCurrent?.right)) -> {
                        wayList.add(tempCurrent)
                        tempCurrent = tempCurrent?.right
                    }
                    (tempCurrent!!.value > oldValue) -> return tempCurrent
                    else -> {
                        if (wayList.size > 0) {
                            checkedNodes.add(tempCurrent)
                            tempCurrent = wayList.last()
                            wayList.remove(wayList.last())
                        } else return null
                    }
                }
            }
        }

        override fun hasNext(): Boolean = findNext() != null

        override fun next(): T {
            current = findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        // T = O(h) R = O(1), где h - высота дерева
        override fun remove() {
            val iterator = this@KtBinaryTree.BinaryTreeIterator()
            var parentNode: Node<T>? = null
            var parentIsLess = false
            while (iterator.hasNext()) {
                iterator.next()
                if (iterator.current?.right != null && iterator.current?.right!! == current) {
                    parentNode = iterator.current
                    parentIsLess = true
                } else if (iterator.current?.left != null && iterator.current?.left!! == current) {
                    parentNode = iterator.current
                }
            }
            when {
                (current?.left == null && current?.right == null) -> {
                    if (current == root) root = null
                    else {
                        if (parentIsLess) parentNode?.right = null
                        else parentNode?.left = null
                    }
                }
                (current?.left == null && current?.right != null) -> {
                    if (current == root) root = current?.right
                    else {
                        if (parentIsLess) parentNode?.right = current?.right
                        else parentNode?.left = current?.right
                    }
                }
                (current?.left != null && current?.right == null) -> {
                    if (current == root) root = current?.left
                    else {
                        if (parentIsLess) parentNode?.right = current?.left
                        else parentNode?.left = current?.left
                    }
                }
                (current?.left != null && current?.right != null) -> {
                    var substituteParent = current
                    var substituteChild: Node<T>? = null
                    if (substituteParent?.right?.left != null) {
                        substituteParent = substituteParent.right
                        while (substituteParent?.left?.left != null) {
                            substituteParent = substituteParent.left
                        }
                        if (substituteParent?.left?.right != null) {
                            substituteChild = substituteParent.left?.right
                        }
                        substituteParent?.left?.left = current?.left
                        substituteParent?.left?.right = current?.right
                        if (current == root) root = substituteParent?.left
                        else {
                            if (parentIsLess) parentNode?.right = substituteParent?.left
                            else parentNode?.left = substituteParent?.left
                        }
                        substituteParent?.left = substituteChild
                    } else {
                        substituteParent?.right?.left = current?.left
                        if (current == root) root = substituteParent?.right
                        else {
                            if (parentIsLess) parentNode?.right = substituteParent?.right
                            else parentNode?.left = substituteParent?.right
                        }
                    }
                }
            }
            size--
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}