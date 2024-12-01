import kotlin.math.abs

fun main() {
    fun parseNumberLists(input: List<String>): Pair<List<Int>, List<Int>> {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        input.forEach { line ->
            val parts = line.split("   ").map { it.toInt() }
            leftList.add(parts[0])
            rightList.add(parts[1])
        }

        return Pair(leftList.sorted(), rightList.sorted())
    }

    fun part1(input: List<String>): Int {
        val (leftList, rightList) = parseNumberLists(input)
        return leftList.mapIndexed { index, number ->
            abs(rightList[index] - number)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val (leftList, rightList) = parseNumberLists(input)
        return leftList.sumOf { number ->
            number * rightList.count { it == number }
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
