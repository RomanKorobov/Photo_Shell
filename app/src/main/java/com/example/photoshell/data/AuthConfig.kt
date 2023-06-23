package com.example.photoshell.data

object AuthConfig {
    const val BASE_URL = "https://api.unsplash.com"
    const val ACCESS_KEY = "JYe8snKzih70lrvly0C3Mdi0ITQvBhY2xvk7c-V5S9I"
    const val SECRET_KEY = "P93TrpijS_2-2J_LcTuHrtaGPSO_3qQa1wrzSRh-OYo"
    const val REDIRECT_URI = "com.example.photoshell://oauth2"
    const val AUTHORIZE_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val SCOPE =
        "public read_user write_user read_photos write_photos write_likes write_followers write_collections read_collections"
}