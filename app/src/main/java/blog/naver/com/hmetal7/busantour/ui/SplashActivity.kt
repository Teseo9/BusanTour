package blog.naver.com.hmetal7.busantour.ui

import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
import blog.naver.com.hmetal7.busantour.BuildConfig
import blog.naver.com.hmetal7.busantour.Constants.permissionList
import blog.naver.com.hmetal7.busantour.R
import blog.naver.com.hmetal7.busantour.databinding.ActivitySplashBinding
import blog.naver.com.hmetal7.busantour.ui.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val activity: AppCompatActivity = this
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    private val viewModel: SplashViewModel by viewModels()

    private val permissionRequest = 90001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkPermission()
    }

    private fun checkPermission() {
        var grantedPermission = true

        // 권한이 있는지 확인.
        for (permission in permissionList) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PERMISSION_GRANTED) {
                grantedPermission = false
            }
        }

        if (grantedPermission) {
            // 권한이 있는 경우.
            downloadServerData()

        } else {
            // 권한이 없는 경우.
            ActivityCompat.requestPermissions(this, permissionList, permissionRequest)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequest -> {
                if (grantResults.isNotEmpty()) {
                    var isGrant = true
                    for (result in grantResults) {
                        if (result == PERMISSION_DENIED) {
                            isGrant = false
                        }
                    }

                    if (isGrant) {
                        downloadServerData()

                    } else {
                        goAppSetting()
                    }

                } else {
                    goAppSetting()
                }
            }
        }
    }

    private fun goAppSetting() {
        Toast.makeText(this, getString(R.string.alert_permission), Toast.LENGTH_SHORT).show()

        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))
        startActivity(intent)
        finish()
    }

    private fun downloadServerData() {
        viewModel.preferenceLanguageType.asLiveData().observe(this) {
            CoroutineScope(Dispatchers.IO).launch {
                val isLoaded = viewModel.loadDataFromServer(it)
                if (isLoaded) {
                    startActivity(Intent(activity, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(activity, getString(R.string.alert_retry), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

}