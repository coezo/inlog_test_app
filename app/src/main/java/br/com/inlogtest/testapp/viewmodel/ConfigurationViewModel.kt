package br.com.inlogtest.testapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.inlogtest.testapp.InlogApplication
import br.com.inlogtest.testapp.model.Configuration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfigurationViewModel : ViewModel() {

    private var disposables = CompositeDisposable()

    private var status = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    var configuration = MutableLiveData<Configuration>()

    fun getConfiguration(context: Context){
        disposables.add((context.applicationContext as InlogApplication).getService().getConfiguration()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{ status.value = true }
            .doFinally { status.value = false }
            .subscribe({
                configuration.value = it.body()
            }, {
                error.value = it.localizedMessage
            })
        )
    }

}