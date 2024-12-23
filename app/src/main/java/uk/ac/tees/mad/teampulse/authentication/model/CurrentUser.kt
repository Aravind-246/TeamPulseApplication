package uk.ac.tees.mad.teampulse.authentication.model

data class CurrentUser(
    val name: String? = null,
    val username: String? = null,
    val phoneNumber: String? = null,
    val profileImgUrl: String? = null
)
