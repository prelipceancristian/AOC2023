package day1

import java.io.File

val nums = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun part1(): Int {
    val lines = File("src/main/resources/day1/input.txt").readLines()
    return lines.sumOf { getLineSum(it) }
}

fun getLineSum(line: String) : Int {
    return getFirstNumFromLeft(line)!! * 10 + getFirstNumFromRight(line)!!
}

fun getLineSumWithStrings(line: String): Int {
    return readFirstNumFromLeft(line) * 10 + readFirstNumFromRight(line)
}

fun getFirstNumFromLeft(line: String) : Int? {
    return line.firstOrNull{ it.isDigit() }?.digitToInt()
}

fun getFirstNumFromRight(line: String): Int? {
    return line.lastOrNull{it.isDigit()}?.digitToInt()
}

fun getFirstStringNumFromLeft(line: String): Int?{
    // [4, 6, 10, 5, -1, 3]
    val indexes = nums.map { line.indexOf(it) }
    if (indexes.all {it == -1})
    {
        return null
    }
    val minValue = indexes.filter { it != -1 }.min()
    return indexes.indexOf(minValue) + 1
}

fun getFirstStringNumFromRight(line: String): Int?{
    val indexes = nums.map { line.lastIndexOf(it) }
    if (indexes.all {it == -1})
    {
        return null
    }
    val maxValue = indexes.filter { it != -1 }.max()
    return indexes.indexOf(maxValue) + 1
}

fun readFirstNumFromLeft(line: String): Int{
    val stringValue = getFirstStringNumFromLeft(line)
    val intValue = getFirstNumFromLeft(line)
    if (stringValue == null)
    {
        return intValue!!
    }
    if (intValue == null)
    {
        return stringValue
    }
    return if(line.indexOf(nums[stringValue - 1]) < line.indexOf((intValue + 48).toChar()))
        stringValue else intValue
}

fun readFirstNumFromRight(line: String): Int {
    val stringValue = getFirstStringNumFromRight(line)
    val intValue = getFirstNumFromRight(line)
    if (stringValue == null)
    {
        return intValue!!
    }
    if (intValue == null)
    {
        return stringValue
    }
    return if(line.lastIndexOf(nums[stringValue - 1]) > line.lastIndexOf((intValue + 48).toChar()))
        stringValue else intValue
}

fun part2(): Int
{
    val lines = File("src/main/resources/day1/input.txt").readLines()
    return lines.sumOf { getLineSumWithStrings(it) }
}


fun main() {
//    println(part1())
    println(part2())
}

