package dev.m13d.itloader

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider.*
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.m13d.itloader.databinding.ActivityMainBinding
import dev.m13d.itloader.navigator.MainNavigator
import dev.m13d.itloader.screen.base.BaseFragment
import dev.m13d.itloader.screen.gallery.GalleryFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navigator by viewModels<MainNavigator> { AndroidViewModelFactory(application) }

    private var downloadedId = 0L
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()

        if (!isFileExists("images.txt")) {
            Log.d("TAG", "Downloading...")
            downloadImageList()
        } else {
            Log.d("TAG", "Not downloading...")
            if (savedInstanceState == null) {
                navigator.launchFragment(
                    this@MainActivity,
                    GalleryFragment.Screen(FULL_FILE_NAME),
                    addToBackStack = false
                )
            }
        }

        binding.topToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.more -> {
                    deleteImage("images.txt")
                    downloadImageList()
                    true
                }
                else -> false
            }
        }

        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadedId) {
                    Log.d("TAG", "Image list download complete")
                    if (savedInstanceState == null) {
                        navigator.launchFragment(
                            this@MainActivity,
                            GalleryFragment.Screen(FULL_FILE_NAME),
                            addToBackStack = false
                        )
                    }
                }
            }
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        navigator.whenActivityActive.mainActivity = this
    }

    override fun onPause() {
        super.onPause()
        navigator.whenActivityActive.mainActivity = null
    }

    private fun deleteImage(fileName: String) {
        val file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + fileName)
        file.delete()
    }

    fun isFileExists(fileName: String): Boolean {
        val file =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + fileName)
        return file?.exists() ?: false
    }

    private fun downloadImageList() {
        val url = "https://it-link.ru/test/images.txt"
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Images")
            .setDescription("Downloading...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, "images.txt")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadedId = dm.enqueue(request)
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }

            val result = navigator.result.value?.getValue() ?: return
            if (f is BaseFragment) {
                f.viewModel.onResult(result)
            }
        }
    }

    companion object {

        @JvmStatic
        private val KEY_RESULT = "RESULT"

        const val FILE_NAME = "images.txt"
        val FULL_FILE_NAME by lazy { (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/" + FILE_NAME)).path }

    }

}
