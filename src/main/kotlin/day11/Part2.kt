package day11

import common.readInputLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = readInputLines(11)
//    val lines = readTestLines(11)
    val universe = lines.map { line -> line.map { it } }
    val emptyLinesIndexes = getEmptyLineIndexes(lines)
    val emptyColumnIndexes = getEmptyColumnIndexes(lines)
    val galaxyPoints = getGalaxyPoints(universe)
    val distances = getGigaDistancesBetweenPoints(galaxyPoints, emptyLinesIndexes, emptyColumnIndexes)
    println(distances.sum())
}

fun getGigaDistancesBetweenPoints(galaxyPoints: List<Pair<Int, Int>>, emptyLinesIndexes: List<Int>, emptyColumnIndexes: List<Int>): List<Long> {
    val distances = mutableListOf<Long>()
    for (i in galaxyPoints.indices) {
        for (j in i+1..<galaxyPoints.size) {
            distances.add(getGigaDistanceBetweenGalaxies(galaxyPoints[i], galaxyPoints[j], emptyLinesIndexes, emptyColumnIndexes))
        }
    }

    return distances
}

fun getGigaDistanceBetweenGalaxies(galaxy1: Pair<Int, Int>, galaxy2: Pair<Int, Int>, emptyLinesIndexes: List<Int>, emptyColumnIndexes: List<Int>): Long {
    val factor = 1000000 - 1
    val emptyLinesBetween = getEmptyCountBetweenIndexes(galaxy1.first, galaxy2.first, emptyLinesIndexes)
    val emptyColumnsBetween = getEmptyCountBetweenIndexes(galaxy1.second, galaxy2.second, emptyColumnIndexes)
    return abs((galaxy1.first - galaxy2.first).toLong()) + abs((galaxy1.second - galaxy2.second).toLong()) + (emptyLinesBetween + emptyColumnsBetween) * factor
}

fun getEmptyCountBetweenIndexes(first: Int, second: Int, indexes: List<Int>): Int {
    val low = min(first, second)
    val high = max(first, second)
    return indexes.count { it in (low + 1)..<high }
}
