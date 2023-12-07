package day5

import common.readInputLines
import common.readTestLines

fun main() {
//    val lines = readTestLines(5)
    val lines = readInputLines(5)
    val seedRanges = readSeedRanges(lines[0])
    val mappings = buildMappings(lines)
//    val locations = mapSeedsToLocation(seeds, mappings)
//    println(locations.min())
}

fun readSeedRanges(line: String): List<SeedRange> {
    return line.split(':')[1]
        .trim()
        .split(' ')
        .chunked(2)
        .map { SeedRange(it[0].toLong(), it[1].toLong()) }
}



fun splitSeedRange(seedRange: SeedRange, index: Long): List<SeedRange> {
    return listOf(SeedRange(seedRange.start, index), SeedRange(index, seedRange.end))
}

data class SeedRange(val start: Long, val end: Long)
