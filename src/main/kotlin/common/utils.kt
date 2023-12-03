package common

import java.io.File

fun readInputLines(day: Int) : List<String>{
    return readLinesFromFile(File("src/main/resources/day${day}/input.txt"))
}

@Suppress("unused")
fun readTestLines(day: Int): List<String> {
    return readLinesFromFile(File("src/main/resources/day${day}/test.txt"))
}

fun readLinesFromFile(file: File): List<String> {
    return file
        .readLines()
        .map {
            it
                .replace("\n", "")
                .replace("\r", "")
                .trim()
        }
}