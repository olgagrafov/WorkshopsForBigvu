package com.olgag.workshopsbigvu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data  class Workshop(
    var name: String? = null,
    var image: String? = null,
    var description: String? = null,
    var text: String? = null,
    var video: String? =null
   ): Parcelable{}