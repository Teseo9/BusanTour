package blog.naver.com.hmetal7.busantour.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import blog.naver.com.hmetal7.busantour.Constants.MAP_BASE_POINT_LATITUDE
import blog.naver.com.hmetal7.busantour.Constants.MAP_BASE_POINT_LONGITUDE
import blog.naver.com.hmetal7.busantour.Constants.MAP_ZOOM_LEVEL_NORMAL
import blog.naver.com.hmetal7.busantour.Constants.languageList
import blog.naver.com.hmetal7.busantour.R
import blog.naver.com.hmetal7.busantour.data.model.BusanInfoItem
import blog.naver.com.hmetal7.busantour.databinding.ActivityMainBinding
import blog.naver.com.hmetal7.busantour.ui.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MainActivity : MapViewActivity() {
    private val activity: AppCompatActivity = this
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setMapView()
        setFilter()

        // 언어 선택 Dialog.
        binding.btnLanguage.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                var choice = when (viewModel.getLanguageType()) {
                    "ko" -> 0
                    "en" -> 1
                    "ja" -> 2
                    "zh" -> 3
                    "tw" -> 4
                    else -> 0
                }

                CoroutineScope(Dispatchers.Main).launch {
                    AlertDialog.Builder(activity)
                        .setTitle("Choose the language.")
                        .setSingleChoiceItems(languageList, choice) { _, which -> choice = which }
                        .setPositiveButton("OK") { dialog, _ ->
                            when (choice) {
                                0 -> changeLocale(dialog, "ko")
                                1 -> changeLocale(dialog, "en")
                                2 -> changeLocale(dialog, "ja")
                                3 -> changeLocale(dialog, "zh")
                                4 -> changeLocale(dialog, "tw")
                                else -> changeLocale(dialog, "ko")
                            }
                        }.show()
                }
            }
        }
    }

    private fun changeLocale(dialog: DialogInterface, type: String) {
        dialog.dismiss()

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setPreferenceLanguageType(type)
            downloadServerData()

            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(type)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }

    private fun downloadServerData() {
        CoroutineScope(Dispatchers.IO).launch {
            val isLoaded = viewModel.loadDataFromServer(viewModel.getLanguageType())
            if (!isLoaded) {
                Toast.makeText(activity, getString(R.string.alert_retry), Toast.LENGTH_SHORT).show()
            } else {
                refreshMapMarker()
            }
        }
    }

    override fun onDestroy() {
        mapView.onSurfaceDestroyed()
        binding.map.removeAllViews()
        super.onDestroy()
    }

    private fun setMapView() {
        mapView = MapView(this)
        mapView.setPOIItemEventListener(this)
        mapView.setMapCenterPointAndZoomLevel(
            mapPointWithGeoCoord(MAP_BASE_POINT_LATITUDE, MAP_BASE_POINT_LONGITUDE),
            MAP_ZOOM_LEVEL_NORMAL, true
        )

        binding.map.addView(mapView)

        viewModel.festivalList.observe(this) {
            addMarker(it, R.drawable.marker_festival)
        }

        viewModel.foodList.observe(this) {
            addMarker(it, R.drawable.marker_food)
        }

        viewModel.sightsList.observe(this) {
            addMarker(it, R.drawable.marker_sights)
        }
    }

    private fun setFilter() {
        // Marker 선택뷰 펼치기/닫기
        binding.btnFilter.setOnClickListener {
            binding.llFilter.isVisible = !binding.llFilter.isVisible
        }

        // Marker Filter 이벤트 처리
        binding.switchFilterFestival.setOnCheckedChangeListener { _, isChecked ->
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.setPreferenceFilterFestival(isChecked)
            }
            refreshMapMarker()
        }
        binding.switchFilterFood.setOnCheckedChangeListener { _, isChecked ->
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.setPreferenceFilterFood(isChecked)
            }
            refreshMapMarker()
        }
        binding.switchFilterSights.setOnCheckedChangeListener { _, isChecked ->
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.setPreferenceFilterSights(isChecked)
            }
            refreshMapMarker()
        }

        // Filter 상태값 Preference 처리.
        viewModel.preferenceFilterFestival.asLiveData().observe(this) {
            binding.switchFilterFestival.isChecked = it
        }

        viewModel.preferenceFilterFood.asLiveData().observe(this) {
            binding.switchFilterFood.isChecked = it
        }

        viewModel.preferenceFilterSights.asLiveData().observe(this) {
            binding.switchFilterSights.isChecked = it
        }
    }

    private fun refreshMapMarker() {
        mapView.removeAllPOIItems()
        if (binding.switchFilterFestival.isChecked) {
            viewModel.getFestivalData()
        }
        if (binding.switchFilterFood.isChecked) {
            viewModel.getFoodData()
        }
        if (binding.switchFilterSights.isChecked) {
            viewModel.getSightsData()
        }
    }

    /**
     * 지도에 마커 추가.
     */
    private fun addMarker(itemList: List<BusanInfoItem>, markerIcon: Int) {
        for (item in itemList) {
            try {
                val marker = MapPOIItem()
                marker.itemName = item.title
                marker.tag = item.id
                marker.mapPoint = mapPointWithGeoCoord(item.latitude!!, item.longitude!!)
                marker.markerType = MapPOIItem.MarkerType.CustomImage
                marker.customImageResourceId = markerIcon
                marker.isCustomImageAutoscale = false
                mapView.addPOIItem(marker)
            } catch (_: Exception) {
            }
        }
    }

    /**
     * 지도에서 마커의 말풍선 누른 경우.
     */
    private var clickable = true

    @SuppressLint("MissingInflatedId")
    override fun onCalloutBalloonOfPOIItemTouched(mapview: MapView?, poiItem: MapPOIItem?) {
        Log.e("ts-test", "onCalloutBalloonOfPOIItemTouched() !!!")
        if (clickable) {
            clickable = false

            poiItem?.let {
                when (it.customImageResourceId) {
                    R.drawable.marker_festival -> {
                        viewBottomSheetFestival(it.tag.toString())
                    }
                    R.drawable.marker_food -> {
                        viewBottomSheetFood(it.tag.toString())
                    }
                    R.drawable.marker_sights -> {
                        viewBottomSheetSights(it.tag.toString())
                    }
                    else -> {
                        viewBottomSheetFestival(it.tag.toString())
                    }
                }
            }
            Handler(Looper.getMainLooper()).postDelayed({ clickable = true }, 500)
        }


    }

    /**
     * 축제 정보의 BottomSheet 보여주기.
     */
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    private fun viewBottomSheetFestival(tag: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getFestivalDataOfTheId(tag).let { item ->
                CoroutineScope(Dispatchers.Main).launch {
                    if (item != null) {
                        val sheetView =
                            layoutInflater.inflate(R.layout.dialog_bottomsheet, null)
                        val bottomSheetDialog = BottomSheetDialog(activity)
                        bottomSheetDialog.setContentView(sheetView)

                        // 메인 이미지
                        Glide.with(activity)
                            .load(item.mainImage)
                            .into(
                                sheetView.findViewById<ImageView>(R.id.iv_main)
                            )

                        // 닫기
                        sheetView.findViewById<Button>(R.id.btn_close)
                            .setOnClickListener {
                                bottomSheetDialog.dismiss()
                            }

                        // 길찾기
                        sheetView.findViewById<Button>(R.id.btn_route)
                            .setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://map.kakao.com/link/to/${item.place},${item.latitude},${item.longitude}")
                                )
                                activity.startActivity(intent)
                            }

                        // 타이틀
                        sheetView.findViewById<TextView>(R.id.tv_title).text =
                            item.title

                        // 컨텐츠
                        sheetView.findViewById<TextView>(R.id.tv_contents).text =
                            item.contents

                        // 운영시간
                        sheetView.findViewById<TextView>(R.id.tv_operatingTime).text =
                            item.operatingTime

                        // 주소
                        sheetView.findViewById<TextView>(R.id.tv_address).text =
                            item.addr1 + "" + item.addr2

                        // 시설
                        sheetView.findViewById<TextView>(R.id.tv_facilities).text =
                            item.facilities

                        // 연락처
                        sheetView.findViewById<TextView>(R.id.tv_contact).text =
                            item.contact

                        bottomSheetDialog.show()

                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.alert_retry),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * 음식점 정보의 BottomSheet 보여주기.
     */
    @SuppressLint("MissingInflatedId")
    private fun viewBottomSheetFood(tag: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getFoodDataOfTheId(tag).let { item ->
                CoroutineScope(Dispatchers.Main).launch {
                    if (item != null) {
                        val sheetView =
                            layoutInflater.inflate(R.layout.dialog_bottomsheet, null)
                        val bottomSheetDialog = BottomSheetDialog(activity)
                        bottomSheetDialog.setContentView(sheetView)

                        // 메인 이미지
                        Glide.with(activity)
                            .load(item.mainImage)
                            .into(
                                sheetView.findViewById<ImageView>(R.id.iv_main)
                            )

                        // 닫기
                        sheetView.findViewById<Button>(R.id.btn_close)
                            .setOnClickListener {
                                bottomSheetDialog.dismiss()
                            }

                        // 길찾기
                        sheetView.findViewById<Button>(R.id.btn_route)
                            .setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://map.kakao.com/link/to/${item.place},${item.latitude},${item.longitude}")
                                )
                                activity.startActivity(intent)
                            }

                        // 타이틀
                        sheetView.findViewById<TextView>(R.id.tv_title).text =
                            item.title

                        // 컨텐츠
                        sheetView.findViewById<TextView>(R.id.tv_contents).text =
                            item.contents

                        // 운영시간
                        sheetView.findViewById<TextView>(R.id.tv_operatingTime).text =
                            item.operatingTime

                        // 주소
                        sheetView.findViewById<TextView>(R.id.tv_address).text =
                            item.addr1 + "" + item.addr2

                        // 대표메뉴
                        sheetView.findViewById<TextView>(R.id.tv_facilities).text =
                            item.representativeMenu

                        // 연락처
                        sheetView.findViewById<TextView>(R.id.tv_contact).text =
                            item.contact

                        bottomSheetDialog.show()

                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.alert_retry),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * 명소 정보의 BottomSheet 보여주기.
     */
    @SuppressLint("MissingInflatedId")
    private fun viewBottomSheetSights(tag: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSightsDataOfTheId(tag).let { item ->
                CoroutineScope(Dispatchers.Main).launch {
                    if (item != null) {
                        val sheetView =
                            layoutInflater.inflate(R.layout.dialog_bottomsheet, null)
                        val bottomSheetDialog = BottomSheetDialog(activity)
                        bottomSheetDialog.setContentView(sheetView)

                        // 메인 이미지
                        Glide.with(activity)
                            .load(item.mainImage)
                            .into(
                                sheetView.findViewById<ImageView>(R.id.iv_main)
                            )

                        // 닫기
                        sheetView.findViewById<Button>(R.id.btn_close)
                            .setOnClickListener {
                                bottomSheetDialog.dismiss()
                            }

                        // 길찾기
                        sheetView.findViewById<Button>(R.id.btn_route)
                            .setOnClickListener {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://map.kakao.com/link/to/${item.place},${item.latitude},${item.longitude}")
                                )
                                activity.startActivity(intent)
                            }

                        // 타이틀
                        sheetView.findViewById<TextView>(R.id.tv_title).text =
                            item.title

                        // 컨텐츠
                        sheetView.findViewById<TextView>(R.id.tv_contents).text =
                            item.contents

                        // 운영시간
                        sheetView.findViewById<TextView>(R.id.tv_operatingTime).text =
                            item.operatingTime

                        // 주소
                        sheetView.findViewById<TextView>(R.id.tv_address).text =
                            item.addr1

                        // 시설
                        sheetView.findViewById<TextView>(R.id.tv_facilities).text =
                            item.facilities

                        // 연락처
                        sheetView.findViewById<TextView>(R.id.tv_contact).text =
                            item.contact

                        bottomSheetDialog.show()

                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.alert_retry),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}