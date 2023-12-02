package day2

import java.io.File

fun main() {
    val lines = File("src/main/resources/day2/input.txt").readLines()
    println(lines.filter { isLineConfigPossible(it) }.sumOf { getLineId(it) })
}

fun getLineId(line: String): Int {
    return line.split(':')[0].split(' ')[1].toInt()
}

fun isLineConfigPossible(line: String): Boolean {
    val gameLines = line
        .split(':')[1]
        .split(';')
        .map { it.trim() }
    return gameLines.all { isGameLineValid(it) }
}

fun isGameLineValid(gameLine: String): Boolean {
    val colorGroups = gameLine.split(',')
    return colorGroups.all { isColorGroupValid(it) }
}

fun isColorGroupValid(colorGroup: String): Boolean {
    val constraints = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )
    val tokens = colorGroup.trim().split(' ')
    val color = tokens[1]
    val cubeCount = tokens[0].toInt()
    return cubeCount <= constraints[color]!!
}

