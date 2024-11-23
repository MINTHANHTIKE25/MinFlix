package com.minthanhtike.minflix.common

import androidx.compose.ui.text.googlefonts.GoogleFont
import com.minthanhtike.minflix.R

object FontProvider {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val fontNameInter= GoogleFont("Inter")
}