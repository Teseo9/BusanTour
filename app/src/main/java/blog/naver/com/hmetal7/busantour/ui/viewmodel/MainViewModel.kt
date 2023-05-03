package blog.naver.com.hmetal7.busantour.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import blog.naver.com.hmetal7.busantour.data.model.FestivalItem
import blog.naver.com.hmetal7.busantour.data.model.FoodItem
import blog.naver.com.hmetal7.busantour.data.model.SightsItem
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BusanTourRepository,
) : ViewModel() {

    // 공공데이터 서버에서 관광 정보를 가져와서 로컬 DB에 저장.
    suspend fun loadDataFromServer(type: String): Boolean {
        val festivalResponse = repository.downloadFestivalDataFromServer(type) // 부산 축제정보 가져오기.
        val foodResponse = repository.downloadFoodDataFromServer() // 부산 음식점정보 가져오기.
        val sightsResponse = repository.downloadSightsDataFromServer() // 부산 명소정보 가져오기.
        return festivalResponse && foodResponse && sightsResponse
    }

    // 로컬 DB에서 불러온 축제 정보.
    val festivalList = MutableLiveData<List<FestivalItem>>()

    // 로컬 DB에서 축제 정보 가져오기.
    fun getFestivalData() = viewModelScope.launch {
        repository.getFestivalData()?.let {
            festivalList.value = it
        }
    }

    // 로컬 DB에서 ID에 해당하는 축제 정보 가져오기.
    suspend fun getFestivalDataOfTheId(id: String): FestivalItem? {
        return repository.getFestivalDataOfTheID(id)
    }

    // 로컬 DB에서 불러온 음식점 정보.
    val foodList = MutableLiveData<List<FoodItem>>()

    // 로컬 DB에서 음식점 정보 가져오기.
    fun getFoodData() = viewModelScope.launch {
        repository.getFoodData()?.let {
            foodList.value = it
        }
    }

    // 로컬 DB에서 ID에 해당하는 음식점 정보 가져오기.
    suspend fun getFoodDataOfTheId(id: String): FoodItem? {
        return repository.getFoodDataOfTheID(id)
    }

    // 로컬 DB에서 불러온 명소 정보.
    val sightsList = MutableLiveData<List<SightsItem>>()

    // 로컬 DB에서 명소 정보 가져오기.
    fun getSightsData() = viewModelScope.launch {
        repository.getSightsData()?.let {
            sightsList.value = it
        }
    }

    // 로컬 DB에서 ID에 해당하는 명소 정보 가져오기.
    suspend fun getSightsDataOfTheId(id: String): SightsItem? {
        return repository.getSightsDataOfTheID(id)
    }

    // Language Type Preference.
    suspend fun getLanguageType(): String {
        return repository.getLanguageType()
    }
    val preferenceLanguageType: Flow<String> = repository.languageType
    suspend fun setPreferenceLanguageType(type: String) {
        repository.updateLanguageType(type)
    }

    // 축제 filter Preference.
    val preferenceFilterFestival: Flow<Boolean> = repository.filterFestival
    suspend fun setPreferenceFilterFestival(isChecked: Boolean) {
        repository.updateFilterFestival(isChecked)
    }

    // 음식점 filter Preference.
    val preferenceFilterFood: Flow<Boolean> = repository.filterFood
    suspend fun setPreferenceFilterFood(isChecked: Boolean) {
        repository.updateFilterFood(isChecked)
    }

    // 명소 filter Preference.
    val preferenceFilterSights: Flow<Boolean> = repository.filterSights
    suspend fun setPreferenceFilterSights(isChecked: Boolean) {
        repository.updateFilterSights(isChecked)
    }
}