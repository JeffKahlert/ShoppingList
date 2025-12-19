package com.example.shoppinglist_public.ui.data.network

import com.example.shoppinglist_public.ui.data.remote.ItemDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Dieser Service dient für die ursprünglichen von Retrofit um die Endpunkte des Spring Boot Backends
 */
interface ShoppingListApiService {

    @GET("api/items")
    suspend fun getAllItems(): List<ItemDTO>

    @POST("api/items")
    suspend fun sendItem(@Body item: ItemDTO): Response<ItemDTO>

    @DELETE("api/items/{uuid}")
    suspend fun removeItem(@Path("uuid") uuid: String): Response<ItemDTO>

    @PUT("api/items/{uuid}")
    suspend fun updateItem(@Path("uuid") uuid: String, @Body item: ItemDTO): Response<ItemDTO>
}
