package day3

import common.readInputLines
//import common.readTestLines

fun main() {
    val lines = readInputLines(3)
//    val lines = readTestLines(3)
    val adjacencyMatrix: Array<BooleanArray> = getAdjacencyMatrix(lines)
//    for (line in adjacencyMatrix)
//    {
//        for (value in line)
//        {
//            print(if(value) 1 else 0)
//        }
//        println()
//    }
    val adjacentNumbers = getAdjacentNumbers(lines, adjacencyMatrix)
//    for (num in adjacentNumbers)
//    {
//        println(num)
//    }
    println(adjacentNumbers.sum())
}

fun getAdjacentNumbers(lines: List<String>, adj: Array<BooleanArray>): List<Int>
{
    val adjacentNumbers = mutableListOf<Int>()
    val height = lines.size
    val width = lines[0].length
    // need to know only the column index, line should be the same
    for (i in 0..<height)
    {
        var isNumberAdjacent = false
        var startIndex = -1
        for(j in 0..<width)
        {
            // go by characters, if a character is the first digit we meet, start a new number
            // every time we are in the number state, check if any coordinates we pass are in the adjacency matrix
            // if we also reach the end of the line, make sure to re check
            // if the number is reached, add to list and return
            if(lines[i][j].isDigit())
            {
                if(startIndex == -1)
                {
                    // first digit we meet
                    // could also do a check for startIndex if -1 instead of this bool
                    isNumberAdjacent = false
                    startIndex = j
                }

                if(adj[i][j])
                {
                    isNumberAdjacent = true
                }
            }
            else
            {
                if(startIndex != -1)
                {
                    // just finished a number
                    if (isNumberAdjacent)
                    {
                        // add number to list
                        adjacentNumbers.add(lines[i].substring(startIndex, j).toInt())
                    }
                    startIndex = -1
                }
            }
        }
        if(startIndex != -1)
        {
            if (isNumberAdjacent)
            {
                // add number to list
                adjacentNumbers.add(lines[i].substring(startIndex, width).toInt())
            }
        }
    }

    return adjacentNumbers
}

fun getAdjacencyMatrix(lines: List<String>): Array<BooleanArray> {
    // matrix of size line count x line length
    val height = lines.size
    val width = lines[0].length
    val adj = Array(height){BooleanArray(width)}
    for(i in 0..<height)
    {
        for(j in 0..<width)
        {
            if (!lines[i][j].isDigit() && lines[i][j] != '.')
            {
                fillNeighbours(adj, i, j)
            }
        }
    }
    return adj
}

fun fillNeighbours(adj: Array<BooleanArray>, i: Int, j: Int) {
    val height = adj.size
    val width = adj[0].size
    for(offset1 in -1..1)
    {
        for(offset2 in -1..1)
        {
            if(areMatrixIndexesValid(i + offset1, j + offset2, width, height))
            {
                adj[i + offset1][j + offset2] = true
            }
        }
    }
}

fun areMatrixIndexesValid(i: Int, j: Int, width: Int, height: Int): Boolean {
    return isWidthIndexValid(i, width) && isHeightIndexValid(j, height)
}

fun isHeightIndexValid(index: Int, height: Int): Boolean {
    return index in 0..<height
}

fun isWidthIndexValid(index: Int, width: Int): Boolean {
    return index in 0..<width
}

