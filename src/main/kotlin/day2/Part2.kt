package day2

import java.io.File

fun main(){
    val lines = File("src/main/resources/day2/input.txt").readLines()
    println(lines.sumOf { getLinePower(it) })
}

fun getLinePower(line: String): Int {
    val constraints = mutableMapOf(
        "red" to 0,
        "green" to 0,
        "blue" to 0
    )
    val gameLines = line
        .split(':')[1]
        .split(';')
        .map { it.trim() }
    for(gameLine in gameLines){
        val colorGroups = gameLine.trim().split(',')
        for(colorGroup in colorGroups){
            val tokens = colorGroup.trim().split(' ')
            val color = tokens[1].trim()
            val cubeCount = tokens[0].toInt()
            if (constraints[color]!! < cubeCount){
                constraints[color] = cubeCount
            }
        }
    }
    return constraints.values.fold(1){next, acc -> acc * next}
}
