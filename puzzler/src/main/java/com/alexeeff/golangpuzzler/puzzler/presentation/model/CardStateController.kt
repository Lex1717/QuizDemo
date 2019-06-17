package com.alexeeff.golangpuzzler.puzzler.presentation.model

import android.util.SparseIntArray

/**
 * Stores answers of a quiz
 *
 * @author Yaroslav Alexeev
 */
const val NO_POSITION = -1

class CardStateController {
    private val stateMap = SparseIntArray()

    fun getSelectedAnswerFor(id: Int): Int {
        return stateMap.get(id, NO_POSITION)
    }

    fun storeSelectedPositionFor(id: Int, selectedPosition: Int) {
        stateMap.put(id, selectedPosition)
    }

    fun reset() {
        stateMap.clear()
    }
}