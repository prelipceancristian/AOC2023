package day9

import common.readInputLines

fun main() {
    val lines = readInputLines(9)
//    val lines = readTestLines(9)
    val numList = lines.map { line -> line.split(' ').map { it.toInt() } }
    println(numList.sumOf { getExtrapolatedValue(it) })
}

fun getExtrapolatedValue(numbers: List<Int>): Int {
    if (numbers.all { it == 0 }) {
        return 0
    }
    val differences = mutableListOf<Int>()
    for (index in numbers.indices) {
        if (index != numbers.size - 1) {
            differences.add(numbers[index + 1] - numbers[index])
        }
    }
    val result = getExtrapolatedValue(differences)
    return numbers.last() + result
}
