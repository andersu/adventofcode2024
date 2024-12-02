import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign


data class Report(
    val numbers: List<Int>
)

fun main() {

    fun parseReports(input: List<String>): List<Report> =
        input.map { line ->
            Report(
                line.split(" ").map { it.toInt() }
            )
        }

    fun Report.diffs() = numbers.subList(1, numbers.size).mapIndexed { i, number ->
        number - numbers[i]
    }

    fun Report.isSafe(): Boolean {
        val diffs = diffs()
        return diffs.map { it.sign }.toSet().size == 1 && diffs.all { abs(it) in 1..3 }
    }

    fun Report.isSafeWithProblemDampener(): Boolean =
        List(numbers.size) { i ->
            Report(numbers.subList(0, i) + numbers.subList(min(i + 1, numbers.size), numbers.size))
        }.any { it.isSafe() }

    fun part1(input: List<String>): Int =
        parseReports(input).count { it.isSafe() }

    fun part2(input: List<String>): Int =
        parseReports(input).count { it.isSafeWithProblemDampener() }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
