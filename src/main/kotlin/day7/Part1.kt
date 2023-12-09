package day7

import common.readInputLines

fun main() {
//    val lines = readTestLines(7)
    val lines = readInputLines(7)
    val hands = readHandsWithBets(lines).sortedWith(HandComparator())
    println(hands.withIndex().sumOf { it.value.bet * (it.index + 1) })
}

fun readHandsWithBets(lines: List<String>): List<Hand> {
    return lines.map {
        Hand(it.split(' ')[0], it.split(' ')[1])
    }
}

val handTypeToValue = mapOf(
    HandType.HIGH_CARD to 1,
    HandType.ONE_PAIR to 2,
    HandType.TWO_PAIR to 3,
    HandType.THREE_OF_A_KIND to 4,
    HandType.FULL_HOUSE to 5,
    HandType.FOUR_OF_A_KIND to 6,
    HandType.FIVE_OF_A_KIND to 7
)

class Hand(val cards: List<Card>, val bet: Int) {
    private val charOrder = arrayOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()

    constructor(hand: String, bet: String) : this(hand.toCharArray().map { Card(it) }, bet.toInt())

    fun getMaxHandType(): HandType {
        if (!cards.any { it.label == 'J' }) {
            return getHandType()
        }
        // ch -> updated hand -> type -> max of
        return charOrder.map { getUpdatedHand(it) }.map { it.getHandType() }.maxBy { handTypeToValue[it]!! }
    }

    private fun getUpdatedHand(char: Char): Hand {
        val updatedCards = this.cards.map { if (it.label == 'J') Card(char) else it }
        return Hand(updatedCards, bet)
    }

    fun getHandType(): HandType {
        val frequencyMap = getFrequencyMap()
        if (frequencyMap.size == 1) {
            return HandType.FIVE_OF_A_KIND
        }
        if (frequencyMap.size == 2) {
            if (frequencyMap.any { it.value == 4 }) {
                return HandType.FOUR_OF_A_KIND
            }
            return HandType.FULL_HOUSE
        }
        if (frequencyMap.size == 3) {
            if (frequencyMap.any { it.value == 3 }) {
                return HandType.THREE_OF_A_KIND
            }
            return HandType.TWO_PAIR
        }
        if (frequencyMap.size == 4) {
            return HandType.ONE_PAIR
        }
        return HandType.HIGH_CARD
    }

    private fun getFrequencyMap(): Map<Char, Int> {
        val frequencyMap = mutableMapOf<Char, Int>()
        for (card in cards) {
            if (card.label in frequencyMap) {
                frequencyMap[card.label] = frequencyMap[card.label]!! + 1
            } else {
                frequencyMap[card.label] = 1
            }
        }

        return frequencyMap
    }

    override fun toString(): String {
        return "${this.cards.map { it.label }} -> ${this.bet}"
    }
}

enum class HandType {
    FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
}

class Card(val label: Char) {
    //    private val valuesArray = arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()
    private val valuesArray = arrayOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()
    val value: Int = valuesArray.indexOf(label)
}

@Suppress("DuplicatedCode")
class HandComparator : Comparator<Hand> {

    override fun compare(o1: Hand?, o2: Hand?): Int {
        if (o1 == null || o2 == null) throw Exception("Good luck explaining this")
        val o1HandValue = handTypeToValue[o1.getHandType()]!!
        val o2HandValue = handTypeToValue[o2.getHandType()]!!

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
