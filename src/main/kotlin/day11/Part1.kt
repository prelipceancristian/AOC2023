package day11

import common.readInputLines
import kotlin.math.abs

fun main() {
//    val lines = readTestLines(11)
    val lines = readInputLines(11)
    val universe = getExpandedUniverse(lines)
    printUniverse(universe)
    val galaxyPoints = getGalaxyPoints(universe)
    val distances = getDistancesBetweenGalaxies(galaxyPoints)
    println(distances.sum())
}

fun getDistancesBetweenGalaxies(galaxyPoints: List<Pair<Int, Int>>): List<Int> {
    val distances = mutableListOf<Int>()
    for (i in galaxyPoints.indices) {
        for (j in i+1..<galaxyPoints.size) {
            distances.add(getDistanceBetweenGalaxies(galaxyPoints[i], galaxyPoints[j]))
        }
    }

    return distances
}

fun getDistanceBetweenGalaxies(galaxy1: Pair<Int, Int>, galaxy2: Pair<Int, Int>): Int {
    return abs(galaxy1.first - galaxy2.first) + abs(galaxy1.second - galaxy2.second)
}

fun getGalaxyPoints(universe: List<List<Char>>): List<Pair<Int, Int>> {
    val points = mutableListOf<Pair<Int, Int>>()
    for (line in universe.withIndex()) {
        for (column in line.value.withIndex()) {
            if (column.value == '#') {
                points.add(Pair(line.index, column.index))
            }
        }
    }

    return points
}

fun getExpandedUniverse(lines: List<String>): List<List<Char>> {
    val universe = mutableListOf<MutableList<Char>>()
    val lineIndexes = getEmptyLineIndexes(lines)
    val columnIndexes = getEmptyColumnIndexes(lines)
    for (i in lines.indices) {
        val currentLine = mutableListOf<Char>()
        for (j in lines[i].indices) {
            currentLine.add(lines[i][j])
            if (j in columnIndexes) {
                currentLine.add('.')
            }
        }
        universe.add(currentLine)
        if (i in lineIndexes) {
            universe.add(currentLine)
        }
    }

    return universe
}

fun getEmptyColumnIndexes(lines: List<String>): List<Int> {
    return (0..<lines[0].length).filter { index ->
            lines.all { line ->
                    line[index] == '.'
                }
        }
}

fun getEmptyLineIndexes(lines: List<String>): List<Int> {
    return lines.indices.filter { index -> lines[index].all { ch -> ch == '.' } }
}

fun printUniverse(universe: List<List<Char>>) {
    for (line in universe) {
        for (ch in line) {
            print(ch)
        }
        println()
    }
}

