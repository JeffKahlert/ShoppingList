package com.example.einkaufsliste.data.network

import com.example.einkaufsliste.data.local.item.Item
import com.example.einkaufsliste.data.remote.ItemDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ShoppingListApiService {

    @GET("api/items")
    suspend fun getAllItems(): List<ItemDTO>

    @POST("api/items")
    suspend fun sendItem(@Body item: ItemDTO): Response<ItemDTO>

    @DELETE("api/items")
    suspend fun removeItem(@Body item: ItemDTO): Response<ItemDTO>

    @PUT("api/items")
    suspend fun updateItem(@Body item: ItemDTO): Response<ItemDTO>
}
