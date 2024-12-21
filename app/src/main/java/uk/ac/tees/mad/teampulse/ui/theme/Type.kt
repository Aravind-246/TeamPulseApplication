package uk.ac.tees.mad.teampulse.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.teampulse.R


val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)


val poppins = GoogleFont("Poppins")

val poppinsFam = FontFamily(
    Font(
        googleFont = poppins,
        fontProvider = provider
    )
)

val oswald = GoogleFont("Oswald")

val oswaldFam = FontFamily(
    Font(
        googleFont = oswald,
        fontProvider = provider
    )
)

val merris = GoogleFont("Merriweather")

val merrisFam = FontFamily(
    Font(googleFont = merris, fontProvider = provider)
)

val fredoka = GoogleFont("Fredoka")

val fredokaFam = FontFamily(
    Font(googleFont = fredoka, fontProvider = provider)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)