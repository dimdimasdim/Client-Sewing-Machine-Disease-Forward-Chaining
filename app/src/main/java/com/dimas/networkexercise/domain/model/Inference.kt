package com.dimas.networkexercise.domain.model


data class Inference(
    val solutions: Solution?,
    val triggeredRule: TriggeredRule?
) {
    data class Solution(
        val code: String,
        val desc: String
    )

    data class TriggeredRule(
        val ruleCode: String,
        val solutionCode: String,
        val triggeredFact: String
    )
}