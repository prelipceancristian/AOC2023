package day6

import common.readInputLines

fun main() {
//        val lines = readTestLines(6)
    val lines = readInputLines(6)
    val time = lines[0].split(":")[1]
        .split(" ")
        .filterNot { it == "" }
        .joinToString(separator = "")
        .toLong()
    val distance = lines[1].split(":")[1]
        .split(" ")
        .filterNot { it == "" }
        .joinToString(separator = "")
        .toLong()
    println(getRaceOutcomes(Pair(time, distance)))
}