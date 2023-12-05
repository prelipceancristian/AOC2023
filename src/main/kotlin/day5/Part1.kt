package day5

import common.readInputLines
import common.readTestLines

fun main() {
//    val lines = readTestLines(5)
    val lines = readInputLines(5)
    val seeds = readSeeds(lines[0])
    val mappings = buildMappings(lines)
    val locations = mapSeedsToLocation(seeds, mappings)
    println(locations.min())
}

fun readSeeds(line: String): List<Long> {
    return line.split(':')[1]
        .trim()
        .split(' ')
        .map { it.toLong() }
}

fun buildIntervals(lines: List<String>): List<Interval> {
    return lines
        .map { it.split(' ') }
        .map { Interval(it[1].toLong(), it[0].toLong(), it[2].toLong()) }
}

fun buildMappings(lines: List<String>): List<List<Interval>> {
    val mapIndexes = lines.withIndex()
        .filter { it.value.isEmpty() }
        .map { it.index }
    val mappingLines = (0..<mapIndexes.size - 1)
        .map { i -> lines.slice(mapIndexes[i]..mapIndexes[i + 1]).drop(2).dropLast(1) }
    return mappingLines.map { buildIntervals(it) }
}

fun mapSeedsToLocation(seeds: List<Long>, mappings: List<List<Interval>>): List<Long> {
    val mapped = seeds.toLongArray()
    for (map in mappings) {
        for (index in mapped.indices) {
            mapped[index] = transformMapped(mapped[index], map)
        }
    }

    return mapped.toList()
}

fun transformMapped(mapped: Long, intervals: List<Interval>): Long {
    for (interval in intervals) {
        if (mapped in interval.intervalStart..<(interval.intervalStart + interval.offset)) {
            val offsetFromIntervalStart = mapped - interval.intervalStart
            return interval.mappingStart + offsetFromIntervalStart
        }
    }
    return mapped
}

data class Interval(val intervalStart: Long, val mappingStart: Long, val offset: Long)
