package com.raywenderlich.redditclient.service

data class FeedResponse(
    val updated: String,
    val icon: String,
    val id: String,
    val logo: String,
    val subtitle: String,
    val title: String,
    val entry: List<EntryResponse>
){
    data class EntryResponse(
        val author: AuthorResponse,
        val category: String,
        val content: String,
        val id: String,
        val updated: String,
        val published: String,
        val title: String
    ){
        data class AuthorResponse(
            val name: String,
            val uri: String
        )
    }
}



