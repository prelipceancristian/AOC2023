package day5

import common.readInputLines

fun main() {
//    val lines = readTestLines(5)
    val lines = readInputLines(5)
    val seedRanges = readSeedRanges(lines[0])
    val mappings = buildMappings(lines)
    val resulSeedRanges = getMappedSeedRanges(seedRanges.toMutableList(), mappings)
    println(resulSeedRanges.minOf { it.start })
}

fun readSeedRanges(line: String): List<SeedRange> {
    return line.split(':')[1]
        .trim()
        .split(' ')
        .chunked(2)
        .map { SeedRange(it[0].toLong(), it[0].toLong() + it[1].toLong() - 1) }
}

fun getMappedSeedRanges(seedRanges: MutableList<SeedRange>, mappings: List<List<Interval>>): MutableList<SeedRange> {
    var resultSeedRanges = seedRanges.toMutableList()
    for(mapping in mappings) {
        resultSeedRanges = splitRanges(resultSeedRanges, mapping)
        // take each seed range. Each seed range MUST belong to a single interval. Determine which interval
        for (index in resultSeedRanges.indices) {
            val seedRange = resultSeedRanges[index]
            if (seedRange.end < mapping[0].intervalStart)
            {
                // before any interval, don't map to anything
                continue
            }
            if (seedRange.start > mapping.last().intervalStart + mapping.last().length - 1) {
                // after any interval, don't map
                continue
            }

            var correspondingInterval = mapping.firstOrNull {it.intervalStart + it.length - 1 >= resultSeedRanges[index].end }
            if (correspondingInterval == null) {
                correspondingInterval = mapping.last {it.intervalStart <= resultSeedRanges[index].start}
            }

            val offset = correspondingInterval.mappingStart - correspondingInterval.intervalStart
            resultSeedRanges[index] = SeedRange(seedRange.start + offset, seedRange.end + offset)
        }
    }

    return resultSeedRanges
}

fun splitRanges(seedRanges: List<SeedRange>, intervals: List<Interval>): MutableList<SeedRange> {
    val result = mutableListOf<SeedRange>()
    val splittingIndexes = getSplittingIndexes(intervals)
    for (seedRange in seedRanges) {
        var start = seedRange.start
        val end = seedRange.end
        var currIndex = splittingIndexes.indexOfFirst { it > start }
        if (currIndex == -1) {
            result.add(SeedRange(start, end))
            continue
        }
        while (currIndex < splittingIndexes.size && end > splittingIndexes[currIndex]) {
            result.add(SeedRange(start, splittingIndexes[currIndex]))
            //these intervals should be disjoint. if they aren't, finding the corresponding interval won't work
            start = splittingIndexes[currIndex] + 1
            currIndex++
        }
        result.add(SeedRange(start, end))
    }

    return result.toMutableList()
}

fun getSplittingIndexes(intervals: List<Interval>): List<Long> {
    return intervals
        .map { listOf(it.intervalStart, it.intervalStart + it.length - 1) }
        .fold(emptyList<Long>()) { first, second -> first + second }
        .distinct()
        .sorted()
}

data class SeedRange(val start: Long, val end: Long)
