package com.dimas.networkexercise.data.response


import com.dimas.networkexercise.domain.model.Inference
import com.google.gson.annotations.SerializedName

data class InferenceResponse(
    @SerializedName("solutions")
    val solutions: List<SolutionResponse?>?,
    @SerializedName("triggered_rules")
    val triggeredRules: List<TriggeredRuleResponse?>?
) {
    fun mapToInference(): Inference {
        val solution = solutions?.map {
            Inference.Solution(
                code = it?.code.orEmpty(),
                desc = it?.desc.orEmpty()
            )
        }?.firstOrNull()

        val triggeredRule = triggeredRules?.map {
            Inference.TriggeredRule(
                ruleCode = it?.ruleCode.orEmpty(),
                solutionCode = it?.solutionCode.orEmpty(),
                triggeredFact = it?.triggeredFact.orEmpty()
            )
        }?.firstOrNull()

        return Inference(
            solutions = solution,
            triggeredRule = triggeredRule
        )
    }

    data class SolutionResponse(
        @SerializedName("code")
        val code: String?,
        @SerializedName("desc")
        val desc: String?,
        @SerializedName("solution_id")
        val solutionId: Int?
    )

    data class TriggeredRuleResponse(
        @SerializedName("rule_code")
        val ruleCode: String?,
        @SerializedName("solution_code")
        val solutionCode: String?,
        @SerializedName("triggered_fact")
        val triggeredFact: String?
    )
}