package day6

import common.readInputLines
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
//    val lines = readTestLines(6)
    val lines = readInputLines(6)
    val times = lines[0].split(":")[1]
        .split(" ")
        .filterNot { it == "" }
        .map { it.toLong() }
    val distances = lines[1].split(":")[1]
        .split(" ")
        .filterNot { it == "" }
        .map { it.toLong() }
    val races = times.zip(distances)
    println(races.map {getRaceOutcomes(it)}.fold(1){first, second -> first * second})
}

fun getRaceOutcomes(race: Pair<Long, Long>): Int {
    val time = race.first.toDouble()
    val distance = race.second.toDouble()
    val delta: Double = time * time - 4 * distance
    val s1 = (time - sqrt(delta)) / 2
    val s2 = (time + sqrt(delta)) / 2
    return countIntegersInOpenInterval(s1, s2)
}

fun countIntegersInOpenInterval(low: Double, high: Double): Int {
    val flooredLow = floor(low)
    var ceiledLow = ceil(low)
    var flooredHigh = floor(high)
    val ceiledHigh = ceil(high)
    if (flooredLow == ceiledLow)
    {
        // low is an Integer, need to make sure he's not part of the open interval
        ceiledLow += 1
    }
    if (flooredHigh == ceiledHigh)
    {
        // high is an Integer, same issue
        flooredHigh -= 1
    }
    return flooredHigh.toInt() - ceiledLow.toInt() + 1
}
