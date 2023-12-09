package day7

import common.readInputLines

fun main() {
//    val lines = readTestLines(7)
    val lines = readInputLines(7)
    val hands = readHandsWithBets(lines)
        .sortedWith(SpecialHandComparator())
    println(hands.withIndex().sumOf { it.value.bet * (it.index + 1) })
}


class SpecialHandComparator: Comparator<Hand> {

    override fun compare(o1: Hand?, o2: Hand?): Int {
        if (o1 == null || o2 == null)
            throw Exception("Good luck explaining this")
        val o1HandValue = handTypeToValue[o1.getMaxHandType()]!!
        val o2HandValue = handTypeToValue[o2.getMaxHandType()]!!

        if (o1HandValue < o2HandValue) {
            return -1
        }
        if (o1HandValue > o2HandValue) {
            return 1
        }

        // start comparing card by card
        for (index in 0..<5) {
            if (o1.cards[index].value < o2.cards[index].value) {
                return -1
            }
            if (o1.cards[index].value > o2.cards[index].value) {
                return 1
            }
        }

        return 0
    }

}