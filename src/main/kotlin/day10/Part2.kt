package day10

import common.readInputLines
import kotlin.math.abs

fun main() {
    val lines = readInputLines(10)
//    val lines = readTestLines(10)
    val maze = lines.map { line -> line.map { it } }
    val startLineIndex = maze.indexOfFirst { it.contains('S') }
    val startColumnIndex = maze[startLineIndex].indexOfFirst { it == 'S' }
    val initialShape = determineStartShape(maze, startLineIndex, startColumnIndex)
    val loop = calculateLoop(maze, startLineIndex, startColumnIndex, initialShape)
    val area = abs(calculateArea(loop))
    println(calculateInteriorIntegers(area, loop.size))
}

fun calculateArea(points: List<Pair<Int, Int>>): Int {
    // Calculate area based on a series of points
    var area = 0
    for (i in points.indices) {
        val firstPoint = points[i]
        val secondPoint = points[if(i == points.size - 1) 0 else i + 1]
        area += (firstPoint.second + secondPoint.second) * (firstPoint.first - secondPoint.first)
    }
    return area / 2
}

fun calculateInteriorIntegers(area: Int, loopSize: Int): Int {
    // Use Pick's theorem
    return area - loopSize / 2 + 1
}