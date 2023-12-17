package day10


import common.readInputLines

fun main() {
//    val lines = readTestLines(10)
    val lines = readInputLines(10)
    val maze = lines.map { line -> line.map { it } }
    val startLineIndex = maze.indexOfFirst { it.contains('S') }
    val startColumnIndex = maze[startLineIndex].indexOfFirst { it == 'S' }
    val initialShape = determineStartShape(maze, startLineIndex, startColumnIndex)
    println(calculateLoopLength(maze, startLineIndex, startColumnIndex, initialShape) / 2)
}

val charToDirections = mapOf(
    '|' to listOf(Directions.UP, Directions.DOWN),
    '-' to listOf(Directions.LEFT, Directions.RIGHT),
    'L' to listOf(Directions.UP, Directions.RIGHT),
    'J' to listOf(Directions.UP, Directions.LEFT),
    '7' to listOf(Directions.DOWN, Directions.LEFT),
    'F' to listOf(Directions.RIGHT, Directions.DOWN),
    '.' to emptyList()
)


fun determineStartShape(maze: List<List<Char>>, i: Int, j: Int): Char {
    val directions = mutableListOf<Directions>()
    if (charToDirections[maze[i - 1][j]]!!.contains(Directions.DOWN)) {
        directions.add(Directions.UP)
    }
    if (charToDirections[maze[i + 1][j]]!!.contains(Directions.UP)) {
        directions.add(Directions.DOWN)
    }
    if (charToDirections[maze[i][j - 1]]!!.contains(Directions.RIGHT)) {
        directions.add(Directions.LEFT)
    }
    if (charToDirections[maze[i][j + 1]]!!.contains(Directions.LEFT)) {
        directions.add(Directions.RIGHT)
    }
    return charToDirections.keys.first { key -> charToDirections[key]!!.containsAll(directions) }
}

enum class Directions {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

fun revertDirection(direction: Directions): Directions {
    if (direction == Directions.DOWN) return Directions.UP
    if (direction == Directions.UP) return Directions.DOWN
    if (direction == Directions.LEFT) return Directions.RIGHT
    return Directions.LEFT
}

fun calculateLoopLength(maze: List<List<Char>>, i: Int, j: Int, initialShape: Char): Int {
    return calculateLoop(maze, i, j, initialShape).size
}

fun calculateLoop(maze: List<List<Char>>, i: Int, j: Int, initialShape: Char): List<Pair<Int, Int>> {
    val points = mutableListOf(Pair(i, j))
    var direction = charToDirections[initialShape]!!.first()
    var (currentX, currentY) = getUpdatedDirections(i, j, direction)
    while (currentX != i || currentY != j) {
        points.add(Pair(currentX, currentY))
        val availableDirections = charToDirections[maze[currentX][currentY]]!!
        direction = revertDirection(direction)
        direction = availableDirections.first { d -> d != direction }
        val res = getUpdatedDirections(currentX, currentY, direction)
        currentX = res.first
        currentY = res.second
    }
    return points
}

fun getUpdatedDirections(i: Int, j: Int, direction: Directions): Pair<Int, Int> {
    if (direction == Directions.UP) {
        return Pair(i - 1, j)
    }
    if (direction == Directions.DOWN) {
        return Pair(i + 1, j)
    }
    if (direction == Directions.RIGHT) {
        return Pair(i, j + 1)
    }
    return Pair(i, j - 1)
}