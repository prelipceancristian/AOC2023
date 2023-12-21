package day12

import common.readInputLines

fun main() {
//    val lines = readTestLines(12)
    val lines = readInputLines(12)
    println(lines.sumOf { getLineOptions(it) })
}

fun getLineOptions(line: String): Int {
    val (springs, conditionsString) = line.split(' ')
    val conditions = conditionsString.split(',').map { it.toInt() }
    return rec(springs, conditions)
}

//val springsToSolutions = mutableMapOf<String, MutableMap<>>

fun rec(springs: String, conditions: List<Int>): Int {
    if (springs.all { it != '?' }) {
        return if (isArrangementValid(springs, conditions)) {
            1
        } else {
            0
        }
    }
    val springs1 = springs.replaceFirst('?', '.')
    val springs2 = springs.replaceFirst('?', '#')
    return rec(springs1, conditions) + rec(springs2, conditions)
}

fun isArrangementValid(springs: String, conditions: List<Int>): Boolean {
    val values = springs.split('.').filter { it != "" }
    if (values.size != conditions.size) {
        return false
    }
    for(index in conditions.indices) {
        if (conditions[index] != values[index].length) {
            return false
        }
    }
    return true
}
