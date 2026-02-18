package com.aniper.app.data.remote.api

import com.aniper.app.data.remote.dto.CharacterDto
import com.aniper.app.data.remote.dto.CharacterListDto
import com.aniper.app.data.remote.dto.SubmitCharacterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MarketApiService {
    @GET("/api/characters")
    suspend fun getAllCharacters(): CharacterListDto

    @GET("/api/characters")
    suspend fun searchCharacters(
        @Query("q") query: String = "",
        @Query("tags") tags: String = ""
    ): CharacterListDto

    @GET("/api/characters/{id}")
    suspend fun getCharacter(@Query("id") id: String): CharacterDto

    @POST("/api/characters/submit")
    suspend fun submitCharacter(@Body character: SubmitCharacterDto): CharacterDto

    @GET("/api/characters/trending")
    suspend fun getTrendingCharacters(): CharacterListDto

    @GET("/api/characters/new")
    suspend fun getNewCharacters(): CharacterListDto
}
