package com.alexeeff.golangpuzzler.puzzler.domain.entity.card

data class Card(
        val id: Int,
        val title: String,
        val optionsList: List<Option>,
        val resultMap: Map<Int, Result>
)