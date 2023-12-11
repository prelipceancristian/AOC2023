package day8

import common.readInputLines

fun lcm(n1: Long, n2: Long): Long {
    var a: Long = n1
    var b: Long = n2
    while (b > 0) {
        val temp: Long = b
        b = a % b // % is remainder
        a = temp
    }
    return n1 / a * n2
}

fun main() {
//    val lines = readTestLines(8)
    val lines = readInputLines(8)
    val instructions = lines[0]
    val nodesMap = getNodesMap(lines.drop(2))
    val stepsMap = nodesMap.keys
        .filter { it.endsWith("A") }
        .associateWith { 0 }
        .toMutableMap()
    for (start in stepsMap.keys) {
        var currentNode = start
        var instructionIndex = 0
        var steps = 0
        while (!currentNode.endsWith("Z")) {
            if (instructionIndex == instructions.length) {
                instructionIndex = 0
            }
            val currentInstruction = instructions[instructionIndex]
            val currentNodeDirections = nodesMap[currentNode]
            currentNode = if (currentInstruction == 'L') currentNodeDirections!!.first else currentNodeDirections!!.second
            instructionIndex++
            steps++
        }
        stepsMap[start] = steps
    }
    println(stepsMap.values
        .map { it.toLong() }
        .fold(1L){first, second -> lcm(first, second)})
}