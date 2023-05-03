package blog.naver.com.hmetal7.busantour.data.repository

import blog.naver.com.hmetal7.busantour.data.model.FestivalItem
import blog.naver.com.hmetal7.busantour.data.model.FoodItem
import blog.naver.com.hmetal7.busantour.data.model.SightsItem

interface BusanTourRepositoryInterface {
    suspend fun downloadFestivalDataFromServer(type: String): Boolean
    suspend fun downloadFoodDataFromServer(): Boolean
    suspend fun downloadSightsDataFromServer(): Boolean
    suspend fun getFestivalData(): List<FestivalItem>?
    suspend fun getFestivalDataOfTheID(autoId: String): FestivalItem?
    suspend fun getFoodData(): List<FoodItem>?

    suspend fun getFoodDataOfTheID(autoId: String): FoodItem?
    suspend fun getSightsData(): List<SightsItem>?
    suspend fun getSightsDataOfTheID(autoId: String): SightsItem?
}