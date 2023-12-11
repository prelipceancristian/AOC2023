package day8

import common.readInputLines

fun main() {
//    val lines = readTestLines(8)
    val lines = readInputLines(8)
    val instructions = lines[0]
    val nodesMap = getNodesMap(lines.drop(2))
    var currentNode = "AAA"
    var instructionIndex = 0
    var steps = 0
    while (currentNode != "ZZZ") {
        if (instructionIndex == instructions.length) {
            instructionIndex = 0
        }
        val currentInstruction = instructions[instructionIndex]
        val currentNodeDirections = nodesMap[currentNode]
        currentNode = if (currentInstruction == 'L') currentNodeDirections!!.first else currentNodeDirections!!.second
        instructionIndex++
        steps++
    }
    println(steps)
}

fun getNodesMap(nodeLines: List<String>) : Map<String, Pair<String, String>> {
    val nodesMap = mutableMapOf<String, Pair<String, String>>()
    for (line in nodeLines) {
        val key = line.substring(0, 3)
        val left = line.substring(7, 10)
        val right = line.substring(12, 15)
        nodesMap[key] = Pair(left, right)
    }
    return nodesMap
}