package blog.naver.com.hmetal7.busantour.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import blog.naver.com.hmetal7.busantour.Constants.TABLE_NAME_FESTIVAL
import blog.naver.com.hmetal7.busantour.Constants.TABLE_NAME_FOOD
import blog.naver.com.hmetal7.busantour.Constants.TABLE_NAME_SIGHTS
import blog.naver.com.hmetal7.busantour.data.model.FestivalItem
import blog.naver.com.hmetal7.busantour.data.model.FoodItem
import blog.naver.com.hmetal7.busantour.data.model.SightsItem

@Dao
interface BusanInfoDao {

    // OnConflictStrategy.REPLACE => Insert 할때 PrimaryKey가 겹치는 것이 있으면 덮어 쓴다는 의미.

    /**
     * 축제 데이터를 로컬 DB에 저장.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFestivalAll(festivalList: List<FestivalItem>?)

    /**
     * 로컬 DB의 축제 데이터 삭제.
     */
    @Query("DELETE FROM $TABLE_NAME_FESTIVAL")
    suspend fun deleteFestivalAll()

    /**
     * 로컬 DB의 축제 데이터 반환.
     */
    @Query("SELECT * FROM $TABLE_NAME_FESTIVAL")
    suspend fun getFestivalDataFromLocal(): List<FestivalItem>?

    /**
     * Id에 맞는 축제 데이터 반환.
     */
    @Query("SELECT * FROM $TABLE_NAME_FESTIVAL WHERE id=:id")
    suspend fun getFestivalDataFromLocalOfTheId(id: String): FestivalItem?



    /**
     * 음식점 데이터를 로컬 DB에 저장.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodAll(foodList: List<FoodItem>?)

    /**
     * 로컬 DB의 음식점 데이터 삭제.
     */
    @Query("DELETE FROM $TABLE_NAME_FOOD")
    suspend fun deleteFoodAll()

    /**
     * 로컬 DB의 음식점 데이터 반환.
     */
    @Query("SELECT * FROM $TABLE_NAME_FOOD")
    suspend fun getFoodDataFromLocal(): List<FoodItem>?

    /**
     * Id에 맞는 음식점 데이터 반환.
     */
    @Query("SELECT * FROM $TABLE_NAME_FOOD WHERE id=:id")
    suspend fun getFoodDataFromLocalOfTheId(id: String): FoodItem?



    /**
     * 명소 데이터를 로컬 DB에 저장.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSightsAll(foodList: List<SightsItem>?)

    /**
     * 로컬 DB의 명소 데이터 삭제.
     */
    @Query("DELETE FROM $TABLE_NAME_SIGHTS")
    suspend fun deleteSightsAll()

    /**
     * 로컬 DB의 명소 데이터 반환.
     */
    @Query("SELECT * FROM $TABLE_NAME_SIGHTS")
    suspend fun getSightsDataFromLocal(): List<SightsItem>?

    /**
     * Id에 맞는 명소 데이터 반환.
     */
    @Query("SELECT * FROM $TABLE_NAME_SIGHTS WHERE id=:id")
    suspend fun getSightsDataFromLocalOfTheId(id: String): SightsItem?





}