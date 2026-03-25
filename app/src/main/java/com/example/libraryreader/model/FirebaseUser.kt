package com.example.libraryreader.model
//
data class FirebaseUser(
    val id: String?,
    val userId: String,
    val displayName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String
){
//Hash map is key value pair format name: "Ratjatji", must map data above in MUser
fun toMap(): MutableMap<String, Any>{
    return mutableMapOf(
        "user_id" to this.userId,
        "display_name" to this.displayName,
        "quote" to this.quote,
        "profession" to this.profession,
        "avatar_url" to this.avatarUrl )
}
}