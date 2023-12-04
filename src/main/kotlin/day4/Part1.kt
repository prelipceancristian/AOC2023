package day4

import common.readInputLines
import kotlin.math.pow

fun main() {
//    val lines = readTestLines(4)
    val lines = readInputLines(4)
    println(lines.sumOf { getGameLinePoints(it) })
}

fun getGameLinePoints(line: String): Int {
    val winningNumbers = getWinningNumbers(line)
    val selectedNumbers = getSelectedNumbers(line)
    val scoreExp: Int? = getCardMatchesScoreExp(selectedNumbers, winningNumbers)
    val base = 2.0
    return if (scoreExp == null) 0 else base.pow(scoreExp).toInt()
}

fun getCardMatchesScoreExp(
    selectedNumbers: List<Int>,
    winningNumbers: List<Int>
): Int? {
    val cardMatches = getCardMatches(selectedNumbers, winningNumbers)
    if (cardMatches == 0) {
        return null
    }
    // 1 match   -> 1
    // 2 matches -> 2
    // 3 matches -> 4
    return cardMatches - 1
}

fun getWinningNumbers(line: String): List<Int> {
    return line
        .substring(line.indexOf(':') + 1, line.indexOf('|'))
        .chunked(3)
        .dropLast(1)
        .map { it.trim().toInt() }
}

fun getSelectedNumbers(line: String): List<Int> {
    return line
        .substring(line.indexOf('|') + 1)
        .chunked(3)
        .map { it.trim().toInt() }
}
