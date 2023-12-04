package day4

import common.readInputLines

fun main() {
    val lines = readInputLines(4)
//    val lines = readTestLines(4)
    val scratchBoardFreq = getScratchBoardFreq(lines)
    println(scratchBoardFreq.sum())
}

fun getScratchBoardFreq(lines: List<String>): IntArray {
    val scratchBoardFreq = IntArray(lines.size).map { 1 }.toMutableList()
    for (index in lines.indices) {
        val winningNumbers = getWinningNumbers(lines[index])
        val selectedNumbers = getSelectedNumbers(lines[index])
        val cardMatches = getCardMatches(selectedNumbers, winningNumbers)
        for (match in 1..cardMatches) {
            scratchBoardFreq[index + match] += scratchBoardFreq[index]
        }
    }
    return scratchBoardFreq.toIntArray()
}

fun getCardMatches(
    selectedNumbers: List<Int>,
    winningNumbers: List<Int>
): Int {
    var matches = 0
    for (selectedNumber in selectedNumbers) {
        if (selectedNumber in winningNumbers) {
            matches++
        }
    }
    return matches
}
