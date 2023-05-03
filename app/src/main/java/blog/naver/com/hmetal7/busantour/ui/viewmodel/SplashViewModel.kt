package blog.naver.com.hmetal7.busantour.ui.viewmodel

import androidx.lifecycle.ViewModel
import blog.naver.com.hmetal7.busantour.data.repository.BusanTourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: BusanTourRepository,
) : ViewModel() {

    val preferenceLanguageType: Flow<String> = repository.languageType

    // 공공데이터 서버에서 관광 정보를 가져와서 로컬 DB에 저장.
    suspend fun loadDataFromServer(type: String): Boolean {
        val festivalResponse = repository.downloadFestivalDataFromServer(type) // 부산 축제정보 가져오기.
        val foodResponse = repository.downloadFoodDataFromServer() // 부산 음식점정보 가져오기.
        val sightsResponse = repository.downloadSightsDataFromServer() // 부산 명소정보 가져오기.
        return festivalResponse && foodResponse && sightsResponse
    }
}