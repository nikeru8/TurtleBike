package com.turtle.turtlebike.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
class UBikeModel : ArrayList<UBikeModelItem>(), Parcelable

@Serializable
@Parcelize
data class UBikeModelItem(

    val act: String = "",
    val ar: String = "",
    val aren: String = "",
    val bemp: Int = 0,
    val infoDate: String = "",
    val infoTime: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val mday: String = "",
    val sarea: String = "",
    val sareaen: String = "",
    val sbi: Int = 0,
    var sna: String = "",
    val snaen: String = "",
    val sno: String = "",
    val srcUpdateTime: String = "",
    val tot: Int = 0,
    val updateTime: String = "",

    ) : Parcelable