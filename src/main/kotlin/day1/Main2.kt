package day1

import java.io.File

val newNums = arrayOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")


fun part2ButBetter(): Int{
    val lines = File("src/main/resources/day1/input.txt").readLines()
    return lines.sumOf { getLineSumButBetter(it) }
}

fun getLineSumButBetter(line: String) : Int {
    return getFirstNumFromLeftButBetter(line) * 10 + getFirstNumFromRightButBetter(line)
}

fun getFirstNumFromLeftButBetter(line: String): Int{
    for(index in line.indices){
        if(line[index].isDigit())
        {
            return line[index].digitToInt()
        }
        for (numIndex in newNums.indices)
        {
            if(line.substring(index).startsWith(newNums[numIndex]))
            {
                return numIndex
            }
        }
    }
    throw Exception()
}


fun getFirstNumFromRightButBetter(line: String): Int{
    for(index in line.indices.reversed()){
        if(line[index].isDigit())
        {
            return line[index].digitToInt()
        }
        for (numIndex in newNums.indices)
        {
            if(line.substring(index).startsWith(newNums[numIndex]))
            {
                return numIndex
            }
        }
    }
    throw Exception()
}

fun main() {
    println(part2ButBetter())
}
