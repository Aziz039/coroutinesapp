package com.example.coroutinesapp

import com.google.gson.annotations.SerializedName

class adviceDetails {
    @SerializedName("slip")
    var slip: datum? = null

    class datum {
        @SerializedName("id")
        var id: Int? = null

        @SerializedName("advice")
        var advice: String? = null
    }
}