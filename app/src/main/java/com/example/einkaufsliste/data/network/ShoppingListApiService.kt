package com.example.einkaufsliste.data.network

import com.example.einkaufsliste.data.local.item.Item
import com.example.einkaufsliste.data.remote.ItemDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShoppingListApiService {

    @GET("api/items")
    suspend fun getAllItems(): List<Item>

    @POST("api/items")
    suspend fun sendAllItems(@Body items: ItemDTO): Response<ItemDTO>
}
