package io.maderski.workmanagerexamples.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import io.maderski.workmanagerexamples.R
import io.maderski.workmanagerexamples.utils.PermissionUtils
import io.maderski.workmanagerexamples.viewmodels.MainViewModel
import io.maderski.workmanagerexamples.workers.NotificationWorker.Companion.NOTIFICATION_WORK_TAG
import io.maderski.workmanagerexamples.workers.SlackPostOnceWorker.Companion.SLACK_WORK_TAG

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
        viewModel.observeWorkFor(SLACK_WORK_TAG) {
            showToast("Slack work: $it")
        }
        viewModel.performSlackWork()
    }

    fun onDemoCancelWorkClicked(view: View) {
        viewModel.observeWorkFor(NOTIFICATION_WORK_TAG) {
            showToast("Notification work: $it")
        }
        viewModel.cancelRunningWork()
    }

    fun onDemoChainsClicked(view: View) {
        viewModel.observeWorkFor(NOTIFICATION_WORK_TAG) {
            showToast("Notification work: $it")
        }
        viewModel.performDemoChainsWork()
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
