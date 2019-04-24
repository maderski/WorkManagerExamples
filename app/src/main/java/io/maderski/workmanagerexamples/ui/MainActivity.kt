package io.maderski.workmanagerexamples.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import io.maderski.workmanagerexamples.R
import io.maderski.workmanagerexamples.utils.PermissionUtils
import io.maderski.workmanagerexamples.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionUtils.checkAllRequiredPermissions(this)
        viewModel = ViewModelProviders.of(this, MainViewModel.ViewModelFactory(this)).get(MainViewModel::class.java)

    }

    fun onSlackWorkClicked(view: View) {
        viewModel.performSlackWork()
        viewModel.observeWorkFor(MainViewModel.SLACK_WORK_TAG) {
            showToast("Slack work: $it")
        }
    }

    fun onCompressionWorkClicked(view: View) {
        viewModel.performCompressionWork()
        viewModel.observeWorkFor(MainViewModel.COMPRESSION_WORK_TAG) {
            showToast("Compression work: $it")
        }
    }

    fun onDemoChainsClicked(view: View) {
        viewModel.performDemoChainsWork()
        viewModel.observeWorkFor(MainViewModel.NOTIFICATION_WORK_TAG) {
            showToast("Compression work: $it")
        }
    }

    private fun showToast(message: String) {
        toast?.let {
            if (it.view.isShown) {
                it.cancel()
            }
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}
