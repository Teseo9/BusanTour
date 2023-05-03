package blog.naver.com.hmetal7.busantour.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import blog.naver.com.hmetal7.busantour.BuildConfig
import blog.naver.com.hmetal7.busantour.Constants.LOAD_SIZE_SERVER
import blog.naver.com.hmetal7.busantour.data.api.BusanTourApi
import blog.naver.com.hmetal7.busantour.data.db.BusanInfoDatabase
import blog.naver.com.hmetal7.busantour.data.model.FestivalItem
import blog.naver.com.hmetal7.busantour.data.model.FoodItem
import blog.naver.com.hmetal7.busantour.data.model.SightsItem
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository.PreferencesKeys.FILTER_FESTIVAL
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository.PreferencesKeys.FILTER_FOOD
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository.PreferencesKeys.FILTER_SIGHTS
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository.PreferencesKeys.TYPE_LANGUAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusanTourRepository @Inject constructor(
    private val api: BusanTourApi,
    private val db: BusanInfoDatabase,
    private val dataStore: DataStore<Preferences>
) : BusanTourRepositoryInterface {

    /**
     * 서버에서 부산 축제 데이터를 가져와서 로컬 데이터베이스에 저장.
     */
    override suspend fun downloadFestivalDataFromServer(type: String): Boolean {
        Log.e("ts-test", "downloadFestivalDataFromServer, type : $type")
        val response = when (getLanguageType()) {
            "en" -> {
                api.searchFestivalForEN(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "ja" -> {
                api.searchFestivalForJA(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "zh" -> {
                api.searchFestivalForZHS(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "tw" -> {
                api.searchFestivalForZHT(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            else -> {
                api.searchFestivalForKR(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
        }

        response.body()?.body?.let { it ->
            it.items.item?.let { itemList ->
                db.getDao().deleteFestivalAll()
                db.getDao().insertFestivalAll(itemList)
                return true
            }
        }

        return false
    }

    /**
     * 서버에서 부산 음식점 데이터를 가져와서 로컬 데이터베이스에 저장.
     */
    override suspend fun downloadFoodDataFromServer(): Boolean {
        val response = when (getLanguageType()) {
            "en" -> {
                api.searchFoodForEN(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "ja" -> {
                api.searchFoodForJA(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "zh" -> {
                api.searchFoodForZHS(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "tw" -> {
                api.searchFoodForZHT(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            else -> {
                api.searchFoodForKR(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
        }

        response.body()?.body?.let { it ->
            it.items.item?.let { itemList ->
                db.getDao().deleteFoodAll()
                db.getDao().insertFoodAll(itemList)
                return true
            }
        }

        return false
    }

    /**
     * 서버에서 부산 명소 데이터를 가져와서 로컬 데이터베이스에 저장.
     */
    override suspend fun downloadSightsDataFromServer(): Boolean {
        val response = when (getLanguageType()) {
            "en" -> {
                api.searchSightsForEN(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "ja" -> {
                api.searchSightsForJA(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "zh" -> {
                api.searchSightsForZHS(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            "tw" -> {
                api.searchSightsForZHT(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
            else -> {
                api.searchSightsForKR(BuildConfig.ServiceKey, 1, LOAD_SIZE_SERVER)
            }
        }

        response.body()?.body?.let { it ->
            it.items.item?.let { itemList ->
                db.getDao().deleteSightsAll()
                db.getDao().insertSightsAll(itemList)
                return true
            }
        }

        return false
    }

    /**
     * 로컬 데이터베이스의 축제 데이터를 반환.
     */
    override suspend fun getFestivalData(): List<FestivalItem>? {
        return db.getDao().getFestivalDataFromLocal()
    }

    /**
     * 로컬 데이터베이스에서 ID에 해당하는 축제 데이터 반환.
     */
    override suspend fun getFestivalDataOfTheID(id: String): FestivalItem? {
        return db.getDao().getFestivalDataFromLocalOfTheId(id)
    }

    /**
     * 로컬 데이터베이스의 음식점 데이터를 반환.
     */
    override suspend fun getFoodData(): List<FoodItem>? {
        return db.getDao().getFoodDataFromLocal()
    }

    /**
     * 로컬 데이터베이스에서 ID에 해당하는 음식점 데이터 반환.
     */
    override suspend fun getFoodDataOfTheID(id: String): FoodItem? {
        return db.getDao().getFoodDataFromLocalOfTheId(id)
    }

    /**
     * 로컬 데이터베이스의 명소 데이터를 반환.
     */
    override suspend fun getSightsData(): List<SightsItem>? {
        return db.getDao().getSightsDataFromLocal()
    }

    /**
     * 로컬 데이터베이스에서 ID에 해당하는 명소 데이터 반환.
     */
    override suspend fun getSightsDataOfTheID(id: String): SightsItem? {
        return db.getDao().getSightsDataFromLocalOfTheId(id)
    }

    // DataStore
    private object PreferencesKeys {
        val TYPE_LANGUAGE = stringPreferencesKey("language_type")
        val FILTER_FESTIVAL = booleanPreferencesKey("filter_festival")
        val FILTER_FOOD = booleanPreferencesKey("filter_food")
        val FILTER_SIGHTS = booleanPreferencesKey("filter_sights")
    }


    val languageType: Flow<String> = dataStore.data.map { prefs ->
        prefs[TYPE_LANGUAGE] ?: "ko"
    }

    suspend fun getLanguageType(): String {
        return dataStore.data.first()[TYPE_LANGUAGE] ?: "ko"
    }

    suspend fun updateLanguageType(type: String) {
        dataStore.edit { prefs ->
            prefs[TYPE_LANGUAGE] = type
        }
    }

    val filterFestival: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[FILTER_FESTIVAL] ?: false
    }

    suspend fun updateFilterFestival(isChecked: Boolean) {
        dataStore.edit { prefs ->
            prefs[FILTER_FESTIVAL] = isChecked
        }
    }

    val filterFood: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[FILTER_FOOD] ?: false
    }

    suspend fun updateFilterFood(isChecked: Boolean) {
        dataStore.edit { prefs ->
            prefs[FILTER_FOOD] = isChecked
        }
    }

    val filterSights: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[FILTER_SIGHTS] ?: false
    }

    suspend fun updateFilterSights(isChecked: Boolean) {
        dataStore.edit { prefs ->
            prefs[FILTER_SIGHTS] = isChecked
        }
    }
}