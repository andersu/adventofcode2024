fun main() {

    fun calculateSumOfMulExpressions(memory: String) =
        "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex().findAll(memory)
            .map { it.value }
            .toList()
            .sumOf { expression ->
                val parts = expression.split(",")
                parts[0].filter { it.isDigit() }.toInt() * parts[1].filter { it.isDigit() }.toInt()
            }

    fun part1(input: List<String>): Int =
        calculateSumOfMulExpressions(input.joinToString(separator = "") { it })

    fun part2(input: List<String>): Int {
        val fullMemory = input.joinToString(separator = "") { it }
        val relevantMemory =
            "don't\\(\\).*?do\\(\\)".toRegex().findAll(fullMemory)
                .map { it.value }
                .fold(fullMemory) { acc, match -> acc.replace(match, "") }

        return calculateSumOfMulExpressions(relevantMemory)
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
