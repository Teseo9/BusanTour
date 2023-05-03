package blog.naver.com.hmetal7.busantour

import android.Manifest

object Constants {

    /**
     * App 권한
     */
    val permissionList = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    /**
     * Language 목록
     */
    val languageList = arrayOf(
        "한국어"/*한국어*/, "English"/*영어*/, "日本語"/*일본어*/, "簡體"/*중국어(간체)*/, "繁體"/*중국어(번체)*/
    )

    /**
     * 지도
     */
    // MAP 초기 위치 (현재 세팅값 : 부산시청)
    const val MAP_BASE_POINT_LATITUDE = 35.179665
    const val MAP_BASE_POINT_LONGITUDE = 129.0747635

    // MAP Zoom Level
    const val MAP_ZOOM_LEVEL_NORMAL = 6

    /**
     * 부산 축제정보
     */
    const val URL_BUSAN_FESTIVAL_KR = "6260000/FestivalService/getFestivalKr" // 한글
    const val URL_BUSAN_FESTIVAL_EN = "6260000/FestivalService/getFestivalEn" // 영문
    const val URL_BUSAN_FESTIVAL_JA = "6260000/FestivalService/getFestivalJa" // 일문
    const val URL_BUSAN_FESTIVAL_ZHS = "6260000/FestivalService/getFestivalZhs" // 중문(간체)
    const val URL_BUSAN_FESTIVAL_ZHT = "6260000/FestivalService/getFestivalZht" // 중문(번체)

    /**
     * 부산 맛집정보
     */
    const val URL_BUSAN_FOOD_KR = "6260000/FoodService/getFoodKr" // 한글
    const val URL_BUSAN_FOOD_EN = "6260000/FoodService/getFoodEn" // 영문
    const val URL_BUSAN_FOOD_JA = "6260000/FoodService/getFoodJa" // 일문
    const val URL_BUSAN_FOOD_ZHS = "6260000/FoodService/getFoodZhs" // 중문(간체)
    const val URL_BUSAN_FOOD_ZHT = "6260000/FoodService/getFoodZht" // 중문(번체)

    /**
     * 부산 명소정보
     */
    const val URL_BUSAN_SIGHTS_KR = "6260000/AttractionService/getAttractionKr" // 한글
    const val URL_BUSAN_SIGHTS_EN = "6260000/AttractionService/getAttractionEn" // 영문
    const val URL_BUSAN_SIGHTS_JA = "6260000/AttractionService/getAttractionJa" // 일문
    const val URL_BUSAN_SIGHTS_ZHS = "6260000/AttractionService/getAttractionZhs" // 중문(간체)
    const val URL_BUSAN_SIGHTS_ZHT = "6260000/AttractionService/getAttractionZht" // 중문(번체)

    // 리스트 호출 건수
    const val LOAD_SIZE_SERVER = 200
    const val LOAD_SIZE = 8

    // Base Url
    const val URL_BASE = "https://apis.data.go.kr/"

    // 로컬 DB 테이블명
    const val TABLE_NAME_FESTIVAL = "festival"
    const val TABLE_NAME_FOOD = "food"
    const val TABLE_NAME_SIGHTS = "sights"

    // DataStore
    const val DATASTORE_SETTINGS = "preferences_settings"
}