package com.example.yassirtest.model

import com.squareup.moshi.Json

data class Genre (
    @field:Json(name = "id") var id : Long,
    @field:Json(name = "name") var name : String
)