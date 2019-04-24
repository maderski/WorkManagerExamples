package io.maderski.workmanagerexamples.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import io.maderski.workmanagerexamples.R
import io.maderski.workmanagerexamples.utils.PermissionUtils
import io.maderski.workmanagerexamples.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionUtils.checkAllRequiredPermissions(this)
        viewModel = ViewModelProviders.of(this, MainViewModel.ViewModelFactory(this)).get(MainViewModel::class.java)

    }

    fun onSlackWorkClicked(view: View) {
        viewModel.performSlackWork()
        viewModel.observeWorkFor(MainViewModel.SLACK_WORK_TAG) {
            Toast.makeText(this, "Slack work: $it", Toast.LENGTH_LONG).show()
        }
    }

    fun onCompressionWorkClicked(view: View) {
        viewModel.performCompressionWork()
        viewModel.observeWorkFor(MainViewModel.COMPRESSION_WORK_TAG) {
            Toast.makeText(this, "Compression work: $it", Toast.LENGTH_LONG).show()
        }
    }

    fun onDemoChainsClicked(view: View) {
        viewModel.performDemoChainDelaysWork()
        viewModel.observeWorkFor(MainViewModel.COMPRESSION_WORK_TAG) {
            Toast.makeText(this, "Demo Chains work: $it", Toast.LENGTH_LONG).show()
        }
    }
}
