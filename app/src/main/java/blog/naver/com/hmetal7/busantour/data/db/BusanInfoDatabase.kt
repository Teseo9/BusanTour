package blog.naver.com.hmetal7.busantour.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import blog.naver.com.hmetal7.busantour.data.model.FestivalItem
import blog.naver.com.hmetal7.busantour.data.model.FoodItem
import blog.naver.com.hmetal7.busantour.data.model.SightsItem

@Database(
    entities = [FestivalItem::class, FoodItem::class, SightsItem::class],
    version = 1,
    exportSchema = false
)
abstract class BusanInfoDatabase : RoomDatabase() {

    abstract fun getDao(): BusanInfoDao

}