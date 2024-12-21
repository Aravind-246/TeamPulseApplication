package uk.ac.tees.mad.teampulse.authentication.model

data class CurrentUser(
    val name: String,
    val phoneNumber: String,
    val profileImgUrl: String
)
