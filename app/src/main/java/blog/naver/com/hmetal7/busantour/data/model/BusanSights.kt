package blog.naver.com.hmetal7.busantour.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import blog.naver.com.hmetal7.busantour.Constants
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

/**
 * 부산 명소정보
 */

@Xml(name = "response")
data class BusanSights(
    @Element(name = "header")
    val header: Header,
    @Element(name = "body")
    val body: SightsBody
)

@Xml(name = "body")
data class SightsBody(
    @Element(name = "items")
    val items: SightsItems,
    @PropertyElement(name = "numOfRows")
    val numOfRows: Int,
    @PropertyElement(name = "pageNo")
    val pageNo: Int,
    @PropertyElement(name = "totalCount")
    val totalCount: Int
)

@Xml(name = "items")
data class SightsItems(
    @Element(name = "item")
    val item: List<SightsItem>?
)

@Entity(tableName = Constants.TABLE_NAME_SIGHTS)
@Xml
data class SightsItem(
    @PrimaryKey(autoGenerate = true)
    @PropertyElement(name = "AUTO_ID")
    override val autoId: Int = 0,
    @PropertyElement(name = "UC_SEQ")
    override var id: Int, // 콘텐츠 ID , ex) 71
    @PropertyElement(name = "MAIN_TITLE")
    var main: String?, // 컨텐츠 이름 , ex) 부산바다축제(한,영, 중간,중번,일)
    @PropertyElement(name = "TITLE")
    override var title: String?, // 제목 , ex) 문화로 노닐다 금정산성축제
    @PropertyElement(name = "SUBTITLE")
    var subTitle: String?, // 부제목 , ex) 축제의 바다 속으로
    @PropertyElement(name = "USAGE_DAY")
    var period: String?, // 운영기간 , ex) 축제 매년 6월 말 ~ 7월 초
    @PropertyElement(name = "USAGE_DAY_WEEK_AND_TIME")
    var operatingTime: String?, // 이용요일 및 시간 ex) 태종대유원지 개방시간 하절기(3월~10월) 04:00~24:00 / 동절기(11월~2월) 05:00~24:00
    @PropertyElement(name = "HLDY_INFO")
    var closedDays: String?, // 휴무일 , ex) 매주 월요일 (투어 등 시설)
    @PropertyElement(name = "PLACE")
    var place: String?, // 장소 , ex) 부산바다축제
    @PropertyElement(name = "GUGUN_NM")
    var gugun: String?, // 구군 , ex) 영도구
    @PropertyElement(name = "ADDR1")
    var addr1: String?, // 주소 , ex) 부산광역시 영도구 전망로 119
    @PropertyElement(name = "LAT")
    override var latitude: Double?, // 위도 , ex) 35.23809
    @PropertyElement(name = "LNG")
    override var longitude: Double?, // 경도 , ex) 129.11713
    @PropertyElement(name = "TRFC_INFO")
    var trafficInformation: String?, // 교통정보 , ex) [개막행사장] 도시철도 1호선 장전역 1,2번 출구 온천천 방향 도보 이동 버스 16, 29, 49, 51, 77, 80, 100, 100-1, 110, 111-1, 121, 131, 144, 301, 1008 장전역 하차
    @PropertyElement(name = "MAIN_IMG_THUMB")
    var thumbnail: String?, // 썸네일 이미지 , ex) https://www.visitbusan.net/uploadImgs/files/cntnts/20191227115551778_thumbL
    @PropertyElement(name = "MAIN_IMG_NORMAL")
    var mainImage: String?, // 메인 이미지 , ex) https://www.visitbusan.net/uploadImgs/files/cntnts/20191213191711585_ttiel
    @PropertyElement(name = "ITEMCNTNTS")
    var contents: String?, // 상세정보 , 소개글
    @PropertyElement(name = "MIDDLE_SIZE_RM1")
    var facilities: String?, // 편의시설 , ex) 장애인 한바다축제 수어통역 / (사)부산장애인총연합회 051-863-0650
    @PropertyElement(name = "USAGE_AMOUNT")
    var fee: String?, // 이용요금 , ex) 무료
    @PropertyElement(name = "CNTCT_TEL")
    var contact: String?, // 연락처 , ex) 051-405-2727
    @PropertyElement(name = "HOMEPAGE_URL")
    var homepage: String? // 홈페이지 주소 , ex) http://guponarufes.co.kr/
) : BusanInfoItem(id, title, autoId, latitude, longitude)
