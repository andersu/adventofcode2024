import kotlin.math.abs

fun main() {

    data class Coordinate(
        val x: Int,
        val y: Int,
        val value: Char
    )

    class WordSearch(val coordinates: List<Coordinate>) {
        fun getColumnAroundX(coordinate: Coordinate): String =
            coordinates.filter { it.x == coordinate.x && it.y in (coordinate.y - 3)..(coordinate.y + 3) }
                .map { it.value }
                .joinToString(separator = "")

        fun getRowAroundX(coordinate: Coordinate): String =
            coordinates.filter { it.y == coordinate.y && it.x in (coordinate.x - 3)..(coordinate.x + 3) }
                .map { it.value }
                .joinToString(separator = "")

        fun getNwseDiagonalAroundCoordinate(coordinate: Coordinate, radius: Int): String =
            coordinates.filter {
                abs(coordinate.x - it.x) <= radius &&
                        abs(coordinate.y - it.y) <= radius &&
                        coordinate.y - it.y == coordinate.x - it.x
            }.map { it.value }
                .joinToString("")

        fun getNeswDiagonalAroundCoordinate(coordinate: Coordinate, radius: Int): String =
            coordinates.filter {
                abs(coordinate.x - it.x) <= radius &&
                        abs(coordinate.y - it.y) <= radius &&
                        it.y - coordinate.y == coordinate.x - it.x
            }.map { it.value }
                .joinToString("")
    }

    fun parseCoordinates(input: List<String>): List<Coordinate> =
        input.flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Coordinate(x = x, y = y, value = char)
            }
        }

    fun String.countXmases(): Int {
        var numberOfXmases = 0
        if (this.contains("XMAS")) {
            numberOfXmases += 1
        }
        if (this.contains("SAMX")) {
            numberOfXmases += 1
        }
        return numberOfXmases
    }

    fun countXmases(wordSearch: WordSearch): Int {
        var numberOfXmases = 0
        val xLetters = wordSearch.coordinates.filter { it.value == 'X' }
        xLetters.forEach {
            val column = wordSearch.getColumnAroundX(it)
            numberOfXmases += column.countXmases()
            val row = wordSearch.getRowAroundX(it)
            numberOfXmases += row.countXmases()
            val nwseDiagonal = wordSearch.getNwseDiagonalAroundCoordinate(it, 3)
            numberOfXmases += nwseDiagonal.countXmases()
            val neswDiagonal = wordSearch.getNeswDiagonalAroundCoordinate(it, 3)
            numberOfXmases += neswDiagonal.countXmases()
        }
        return numberOfXmases
    }

    fun countMasXes(wordSearch: WordSearch): Int {
        val aLetters = wordSearch.coordinates.filter { it.value == 'A' }
        val masVariants = listOf("SAM", "MAS")
        return aLetters.count {
            val nwseDiagonal = wordSearch.getNwseDiagonalAroundCoordinate(it, 1)
            val neswDiagonal = wordSearch.getNeswDiagonalAroundCoordinate(it, 1)
            nwseDiagonal in masVariants && neswDiagonal in masVariants
        }
    }

    fun part1(input: List<String>): Int =
        countXmases(WordSearch(parseCoordinates(input)))

    fun part2(input: List<String>): Int =
        countMasXes(WordSearch(parseCoordinates(input)))

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
