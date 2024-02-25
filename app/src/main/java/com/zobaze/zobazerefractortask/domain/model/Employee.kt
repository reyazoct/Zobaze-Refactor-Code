package com.zobaze.zobazerefractortask.domain.model

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("employee_name")
    val employeeName: String?


) {
    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (employeeName?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        return employeeName == other.employeeName
    }
}