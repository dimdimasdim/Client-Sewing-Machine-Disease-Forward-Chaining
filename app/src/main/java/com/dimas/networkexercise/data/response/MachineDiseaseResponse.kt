package com.dimas.networkexercise.data.response


import com.dimas.networkexercise.domain.model.MachineDisease
import com.google.gson.annotations.SerializedName

data class MachineDiseaseResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("desc")
    val dsc: String?,
    @SerializedName("id")
    val id: Int?
) {

    fun mapToMachineDisease(): MachineDisease {
        return MachineDisease(
            code = code.orEmpty(),
            desc = dsc.orEmpty()
        )
    }
}

fun List<MachineDiseaseResponse>?.mapToDisease(): List<MachineDisease> {
    return this?.map { it.mapToMachineDisease() }.orEmpty()
}
