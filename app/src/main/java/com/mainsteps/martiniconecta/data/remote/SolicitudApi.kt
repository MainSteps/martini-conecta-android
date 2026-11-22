package com.mainsteps.martiniconecta.data.remote

import com.mainsteps.martiniconecta.model.SolicitudContacto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SolicitudApi {

    @GET("api/solicitudes")
    suspend fun listar(@Query("telefono") telefono: String? = null): List<SolicitudContacto>

    @GET("api/solicitudes/{id}")
    suspend fun obtener(@Path("id") id: Long): SolicitudContacto

    @POST("api/solicitudes")
    suspend fun crear(@Body body: SolicitudContacto): SolicitudContacto

    @PUT("api/solicitudes/{id}")
    suspend fun actualizar(@Path("id") id: Long, @Body body: SolicitudContacto): SolicitudContacto

    @DELETE("api/solicitudes/{id}")
    suspend fun eliminar(@Path("id") id: Long): Response<Unit>
}