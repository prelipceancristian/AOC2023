package day3

import common.readInputLines

fun main() {
    // line -> list of pairs of indexes
    // find each * by index
    // look in lines above, same and below
    // count touching pairs
    // if count is good, read from line and indexes, sum up, done
//    val lines = readTestLines(3)
    val lines = readInputLines(3)
    val indexes = lines.map { getIndexPairsFromLine(it) }
//    for (lst in indexes) {
//        for (pair in lst) {
//            print("(${pair.first}, ${pair.second}) ")
//        }
//        println()
//    }
    val gearRatios = getGearRatios(lines, indexes)
    println(gearRatios)
    println(gearRatios.sum())
}

fun getIndexPairsFromLine(line: String): List<Pair<Int, Int>> {
    val pairs = mutableListOf<Pair<Int, Int>>()
    var startIndex = -1
    for (index in line.indices) {
        if (line[index].isDigit()) {
            if (startIndex == -1) {
                startIndex = index
            }
        } else if (startIndex != -1) {
            pairs.add(Pair(startIndex, index))
            startIndex = -1
        }
    }
    if (line.last().isDigit() && startIndex != -1) {
        pairs.add(Pair(startIndex, line.length))
    }
    return pairs
}

fun getAdjacentNumberPairs(i: Int, j: Int, indexes: List<List<Pair<Int, Int>>>): List<Pair<Int, Pair<Int, Int>>> {
    val adjacentPairs = mutableListOf<Pair<Int, Pair<Int, Int>>>()
    val previousLineIndexes = if (i == 0) emptyList() else indexes[i - 1]
    adjacentPairs.addAll(previousLineIndexes.filter { isNumberPairTouchingGear(it, j) }.map { Pair(i - 1, it) })
    val currentLineIndexes = indexes[i]
    adjacentPairs.addAll(currentLineIndexes.filter { isNumberPairTouchingGear(it, j) }.map { Pair(i, it) })
    val nextLineIndexes = if (i == indexes.size - 1) emptyList() else indexes[i + 1]
    adjacentPairs.addAll(nextLineIndexes.filter { isNumberPairTouchingGear(it, j) }.map { Pair(i + 1, it) })
    return adjacentPairs
}

fun isNumberPairTouchingGear(pair: Pair<Int, Int>, j: Int): Boolean {
    // either value in the pair is in range j-1 j+1 OR (left < j - 1 AND j + 1 < right)
    if (pair.first in j - 1..j + 1) {
        return true
    }
    // since the second value in the pair is the one used for the substring method,
    // this means the condition needs to be slightly adjusted
    // (1 4) 5
    if (pair.second in j..j + 2) {
        return true
    }
    // same here, but
    if (pair.first < j - 1 && pair.second > j + 2) {
        return true
    }
    return false
}

fun getGearRatios(lines: List<String>, indexes: List<List<Pair<Int, Int>>>): List<Int> {
    val ratios = mutableListOf<Int>()
    for (lineIndex in lines.indices) {
        for (characterIndex in lines[lineIndex].indices) {
            if (lines[lineIndex][characterIndex] == '*') {
                val adjacentNumberPairs = getAdjacentNumberPairs(lineIndex, characterIndex, indexes)
                if (adjacentNumberPairs.size == 2) {
                    val firstNum =
                        lines[adjacentNumberPairs[0].first].substring(
                            adjacentNumberPairs[0].second.first,
                            adjacentNumberPairs[0].second.second
                        ).toInt()
                    val secondNUm =
                        lines[adjacentNumberPairs[1].first].substring(
                            adjacentNumberPairs[1].second.first,
                            adjacentNumberPairs[1].second.second
                        ).toInt()
                    ratios.add(firstNum * secondNUm)
                }
            }
        }
    }
    return ratios
}