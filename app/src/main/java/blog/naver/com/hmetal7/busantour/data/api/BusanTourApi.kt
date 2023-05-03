package blog.naver.com.hmetal7.busantour.data.api

import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FESTIVAL_EN
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FESTIVAL_JA
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FESTIVAL_KR
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FESTIVAL_ZHS
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FESTIVAL_ZHT
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FOOD_EN
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FOOD_JA
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FOOD_KR
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FOOD_ZHS
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_FOOD_ZHT
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_SIGHTS_EN
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_SIGHTS_JA
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_SIGHTS_KR
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_SIGHTS_ZHS
import blog.naver.com.hmetal7.busantour.Constants.URL_BUSAN_SIGHTS_ZHT
import blog.naver.com.hmetal7.busantour.data.model.BusanFestival
import blog.naver.com.hmetal7.busantour.data.model.BusanFood
import blog.naver.com.hmetal7.busantour.data.model.BusanSights
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BusanTourApi {

    /**
     * 부산 축제정보
     */

    @GET(URL_BUSAN_FESTIVAL_KR)
    suspend fun searchFestivalForKR(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFestival>

    @GET(URL_BUSAN_FESTIVAL_EN)
    suspend fun searchFestivalForEN(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFestival>

    @GET(URL_BUSAN_FESTIVAL_JA)
    suspend fun searchFestivalForJA(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFestival>

    @GET(URL_BUSAN_FESTIVAL_ZHS)
    suspend fun searchFestivalForZHS(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFestival>

    @GET(URL_BUSAN_FESTIVAL_ZHT)
    suspend fun searchFestivalForZHT(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFestival>


    /**
     * 부산 맛집정보
     */

    @GET(URL_BUSAN_FOOD_KR)
    suspend fun searchFoodForKR(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFood>

    @GET(URL_BUSAN_FOOD_EN)
    suspend fun searchFoodForEN(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFood>

    @GET(URL_BUSAN_FOOD_JA)
    suspend fun searchFoodForJA(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFood>

    @GET(URL_BUSAN_FOOD_ZHS)
    suspend fun searchFoodForZHS(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFood>

    @GET(URL_BUSAN_FOOD_ZHT)
    suspend fun searchFoodForZHT(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanFood>


    /**
     * 부산 명소정보
     */

    @GET(URL_BUSAN_SIGHTS_KR)
    suspend fun searchSightsForKR(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanSights>

    @GET(URL_BUSAN_SIGHTS_EN)
    suspend fun searchSightsForEN(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanSights>

    @GET(URL_BUSAN_SIGHTS_JA)
    suspend fun searchSightsForJA(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanSights>

    @GET(URL_BUSAN_SIGHTS_ZHS)
    suspend fun searchSightsForZHS(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanSights>

    @GET(URL_BUSAN_SIGHTS_ZHT)
    suspend fun searchSightsForZHT(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int
    ): Response<BusanSights>

}