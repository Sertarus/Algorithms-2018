package lesson1

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Tag
import java.io.BufferedWriter
import java.io.File
import java.util.*
import kotlin.math.abs
import kotlin.test.Test

class TaskTestsKotlin : AbstractTaskTests() {

    @Test
    @Tag("Easy")
    fun testSortTimes() {
        sortTimes { inputName, outputName -> sortTimes(inputName, outputName) }
    }

    @Test
    @Tag("Normal")
    fun testSortAddresses() {
        try {
            sortAddresses("input/addr_in1.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                    Железнодорожная 3 - Петров Иван
                    Железнодорожная 7 - Иванов Алексей, Иванов Михаил
                    Садовая 5 - Сидоров Петр, Сидорова Мария
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortAddresses("input/blank.txt", "temp.txt")
            assertFileContent("temp.txt", "")
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortAddresses("input/addr_in3.txt", "temp.txt")
            assertFileContent("temp.txt", File("input/addr_out3.txt").readLines().joinToString(separator = "\n"))
        } finally {
            File("temp.txt").delete()
        }
    }

    private fun generateTemperatures(size: Int) {
        val random = Random()
        val temperatures = mutableListOf<Int>()
        for (t in -2730..5000) {
            val count = random.nextInt(size)
            for (i in 1..count) {
                temperatures += t
            }
        }

        fun BufferedWriter.writeTemperatures() {
            for (t in temperatures) {
                if (t < 0) write("-")
                write("${abs(t) / 10}.${abs(t) % 10}")
                newLine()
            }
            close()
        }

        File("temp_sorted_expected.txt").bufferedWriter().writeTemperatures()
        temperatures.shuffle(random)
        File("temp_unsorted.txt").bufferedWriter().writeTemperatures()
    }

    @Test
    @Tag("Normal")
    fun testSortTemperatures() {
        try {
            sortTemperatures("input/temp_in1.txt", "temp.txt")
            assertFileContent("temp.txt",
                    """
                    -98.4
                    -12.6
                    -12.6
                    11.0
                    24.7
                    99.5
                    121.3
                """.trimIndent()
            )
        } finally {
            File("temp.txt").delete()
        }
        try {
            sortAddresses("input/blank.txt", "temp.txt")
            assertFileContent("temp.txt", "")
        } finally {
            File("temp.txt").delete()
        }
        try {
            generateTemperatures(500)
            sortTemperatures("temp_unsorted.txt", "temp_sorted_actual.txt")
            assertFileContent("temp_sorted_actual.txt",
                    File("temp_sorted_expected.txt").readLines().joinToString(separator = "\n")
            )
        } finally {
            File("temp_unsorted.txt").delete()
            File("temp_sorted_expected.txt").delete()
            File("temp_sorted_actual.txt").delete()
        }
    }

    @Test
    @Tag("Normal")
    fun testSortSequence() {
        sortSequence { inputName, outputName -> sortSequence(inputName, outputName) }
    }

    @Test
    @Tag("Easy")
    fun testMergeArrays() {
        val result = arrayOf(null, null, null, null, null, 1, 3, 9, 13, 18, 23)
        mergeArrays(arrayOf(4, 9, 15, 20, 23), result)
        assertArrayEquals(arrayOf(1, 3, 4, 9, 9, 13, 15, 18, 20, 23, 23), result)

        run {
            val (first, second, expectedResult) = generateArrays(20000, 20000)
            mergeArrays(first, second)
            assertArrayEquals(expectedResult, second)
        }

        run {
            val (first, second, expectedResult) = generateArrays(500000, 500000)
            mergeArrays(first, second)
            assertArrayEquals(expectedResult, second)
        }
    }
}