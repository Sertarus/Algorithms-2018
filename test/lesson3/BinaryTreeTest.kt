package lesson3

import org.junit.jupiter.api.Tag
import java.util.*
import kotlin.test.*

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testAdd { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            binarySet.remove(toRemove)
            println("Removing $toRemove from $list")
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
    }

    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next())
            }
        }
    }

    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
        val test = KtBinaryTree<Int>()
        val ktIterator = test.BinaryTreeIterator()
        assertFails {
            ktIterator.next()
        }
        val testList = mutableListOf<Int>()
        testList.add(25)
        testList.add(81)
        testList.add(38)
        testList.add(71)
        testList.add(37)
        testList.add(21)
        testList.add(92)
        testList.add(74)
        testList.add(27)
        testList.add(67)
        testList.add(29)
        testList.add(55)
        testList.add(76)
        testList.add(57)
        testList.add(98)
        testList.add(15)
        testList.add(9)
        testList.add(48)
        for (element in testList) {
            test.add(element)
        }
        val resultList = mutableListOf<Int>()
        while (ktIterator.hasNext()) resultList.add(ktIterator.next())
        assertEquals(testList.sorted(), resultList)
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(inn, element in binarySet,
                        "$element should be ${if (inn) "in" else "not in"} tree")
            }
            assertTrue(binarySet.checkInvariant())
        }
        val test = KtBinaryTree<Int>()
        val ktIterator = test.iterator()
        assertFails {
            ktIterator.remove()
        }
        val testTree = TreeSet<Int>()
        for (i in 1000 downTo 0) testTree.add(i)
        for (element in testTree) {
            test.add(element)
        }
        testTree.remove(445)
        while (ktIterator.hasNext()) {
            val element = ktIterator.next()
            if (element == 445)
                ktIterator.remove()
        }
        assertEquals<SortedSet<*>>(testTree, test)
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
    }
}