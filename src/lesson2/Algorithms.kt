@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import java.lang.StringBuilder

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    TODO()
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
//T = O(firstLength*secondLength) R=O(1)
fun longestCommonSubstring(first: String, second: String): String {
    var longestSubstringLength = 0
    var longestSubstringEnd = -1
    var firstCurrentIndex = 0
    var secondCurrentIndex = 0
    var currentSubstringLength = 0
    var nextCellAfterWordSearching = Pair(-1, -1)

    while (firstCurrentIndex != first.length) {
        while (secondCurrentIndex != second.length) {
            if (longestSubstringLength >= second.length - secondCurrentIndex && currentSubstringLength == 0) break
            if (first[firstCurrentIndex] == second[secondCurrentIndex]) {
                if (nextCellAfterWordSearching.first == -1) {
                    nextCellAfterWordSearching = Pair(firstCurrentIndex, secondCurrentIndex + 1)
                }
                if (firstCurrentIndex < first.length - 1 && secondCurrentIndex < second.length - 1) {
                    firstCurrentIndex++
                    secondCurrentIndex++
                    currentSubstringLength++
                } else {
                    if (currentSubstringLength > longestSubstringLength) {
                        longestSubstringEnd = firstCurrentIndex
                        longestSubstringLength = currentSubstringLength + 1
                    }
                    firstCurrentIndex = nextCellAfterWordSearching.first
                    secondCurrentIndex = nextCellAfterWordSearching.second
                    nextCellAfterWordSearching = Pair(-1, -1)
                    currentSubstringLength = 0
                }
            } else {
                if (currentSubstringLength > longestSubstringLength) {
                    longestSubstringEnd = firstCurrentIndex - 1
                    longestSubstringLength = currentSubstringLength
                }
                if (currentSubstringLength > 0) {
                    firstCurrentIndex = nextCellAfterWordSearching.first
                    secondCurrentIndex = nextCellAfterWordSearching.second
                    nextCellAfterWordSearching = Pair(-1, -1)
                    currentSubstringLength = 0
                } else {
                    secondCurrentIndex++
                }
            }
        }
        firstCurrentIndex++
        secondCurrentIndex = 0
    }
    return first.substring(longestSubstringEnd - longestSubstringLength + 1..longestSubstringEnd)
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    TODO()
}

/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun findLetter(row: Int, column: Int, matrix: List<MutableList<Char>>, usedCells: MutableSet<Pair<Int, Int>>,
               word: String, currentLetter: Int): MutableList<Pair<Int, Int>> {
    val resultDirectionList = mutableListOf<Pair<Int, Int>>()
    val directions = arrayOf(Pair(1, 0), Pair(0, 1), Pair(-1, 0), Pair(0, -1))
    directions.forEach { direction ->
        var currentRow = row
        var currentColumn = column
        currentRow += direction.first
        currentColumn += direction.second
        if (Pair(currentRow, currentColumn) !in usedCells) {
            if (currentRow >= 0 && currentColumn >= 0 && currentRow < matrix.size && currentColumn < matrix[0].size) {
                if (matrix[currentRow][currentColumn] == word[currentLetter]) {
                    resultDirectionList.add(direction)
                }
            }
        }
    }
    return resultDirectionList
}

// T = O(m*n*words.size) m - количество столбцов матрицы n - количестов строк R = O(N)
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    val matrix = mutableListOf<MutableList<Char>>()
    val foundWords = mutableSetOf<String>()
    for ((rowCounter, line) in File(inputName).readLines().withIndex()) {
        matrix.add(mutableListOf())
        for (i in 0..line.length step 2) {
            matrix[rowCounter].add(line[i])
        }
    }
    for (row in 0 until matrix.size) {
        for (column in 0 until matrix[0].size) {
            words.forEach { word ->
                if (word[0] == matrix[row][column] && !foundWords.contains(word)) {
                    var currentRow = row
                    var currentColumn = column
                    val potentialWord = StringBuilder().append(word[0])
                    var currentLetter = 1
                    val wayList = mutableListOf<Pair<Int, Int>>()
                    val checkedCells = mutableSetOf<Pair<Int, Int>>()
                    val usedCells = mutableSetOf(Pair(row, column))
                    do {
                        if (Pair(currentRow, currentColumn) !in checkedCells) {
                            wayList.addAll(findLetter(currentRow, currentColumn, matrix,
                                    usedCells, word, currentLetter))
                            checkedCells.add(Pair(currentRow, currentColumn))
                        }
                        if (wayList.isNotEmpty()) {
                            currentRow += wayList.last().first
                            currentColumn += wayList.last().second
                            potentialWord.append(word[currentLetter])
                            currentLetter++
                            usedCells.add(Pair(currentRow, currentColumn))
                            if (currentLetter < word.length && findLetter(currentRow, currentColumn, matrix,
                                            usedCells, word, currentLetter).isEmpty()) {
                                usedCells.remove(Pair(currentRow, currentColumn))
                                currentRow -= wayList.last().first
                                currentColumn -= wayList.last().second
                                currentLetter--
                                potentialWord.deleteCharAt(potentialWord.length - 1)
                                wayList.removeAt(wayList.size - 1)
                            }
                            if (potentialWord.toString() == word) {
                                foundWords.add(word)
                            }
                        }
                    } while (word !in foundWords && wayList.isNotEmpty())
                }
            }
        }
    }
    return foundWords
}