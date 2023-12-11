package day9

import common.readInputLines

fun main() {
    val lines = readInputLines(9)
//    val lines = readTestLines(9)
    val numList = lines.map { line -> line.split(' ').map { it.toInt() }.reversed() }
    println(numList.sumOf { getExtrapolatedValue(it) })
}