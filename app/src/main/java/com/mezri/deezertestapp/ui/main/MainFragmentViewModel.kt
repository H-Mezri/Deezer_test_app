package com.mezri.deezertestapp.ui.main

import androidx.lifecycle.MutableLiveData
import com.mezri.deezertestapp.data.errors.AppMessages
import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.repository.Repository
import com.mezri.deezertestapp.ui.base.BaseViewModel
import com.mezri.deezertestapp.utils.idling.EspressoIdlingResource
import com.mezri.deezertestapp.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainFragmentViewModel : BaseViewModel() {

    // Schedulers
    private lateinit var schedulerProvider: BaseSchedulerProvider
    // view model repository
    private lateinit var repository: Repository
    // composite to handle requests
    private var compositeDisposable: CompositeDisposable? = null

    // list of albums in cache
    val albumsListCache = mutableListOf<Album>()

    // indicate if there is already a loading album request
    var isLoadingAlbums: Boolean = false

    // live data for fragment
    val albumsList = MutableLiveData<List<Any>>().apply { value = mutableListOf() }
    var isFirstRequestLoadingAlbums = MutableLiveData<Boolean>().apply { value = true }
    var isNoAlbumLoaded = MutableLiveData<Boolean>().apply { value = false }

    /**
     * inject dependencies into view model (repository, schedulers, init composite disposal )
     * {in a bigger project we can use Dagger2 to inject repository in view models}
     */
    fun initRepository(repository: Repository, schedulerProvider: BaseSchedulerProvider) {
        this.repository = repository
        this.schedulerProvider = schedulerProvider
        compositeDisposable = CompositeDisposable()
    }

    /**
     * Send get albums request and handle the response
     */
    fun getAlbums() {
        // Inform Espresso that app is busy
        EspressoIdlingResource.increment()
        // notify app is loading data
        isLoadingAlbums = true
        // check is has previous error
        if (isNoAlbumLoaded.value!!) {
            isNoAlbumLoaded.value = false
            isFirstRequestLoadingAlbums.value = true
        }

        // call network service to load albums
        compositeDisposable?.add(
            repository.getAlbums(albumsListCache.size)
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe({ albums ->
                    when {
                        albums.isNotEmpty() -> {
                            // add request result to albums list
                            albumsList.value = albums
                            albumsListCache.addAll(albums)
                            isFirstRequestLoadingAlbums.value = false
                        }
                        albumsListCache.size == 0 -> {
                            // in case no albums found
                            albumsList.value = emptyList()
                            // handle error
                            handleAppMessage(AppMessages.ALBUMS_NOT_FOUND.getAppError())
                        }
                        else -> {
                            // in case no more albums to load
                            albumsList.value = emptyList()
                        }
                    }
                    // notify app finished loading data
                    isLoadingAlbums = false
                    // Inform Espresso that app is ready
                    EspressoIdlingResource.decrement()
                }, { throwable ->
                    if (albumsListCache.isEmpty()) {
                        // notify request received
                        isNoAlbumLoaded.value = true
                    }
                    isFirstRequestLoadingAlbums.value = false
                    // notify app finished loading data
                    albumsList.value = emptyList()
                    isLoadingAlbums = false
                    // handle error
                    handleAppMessage(AppMessages.NETWORK_ERROR.getAppError(throwable))
                    // Inform Espresso that app is ready
                    EspressoIdlingResource.decrement()
                })
        )
    }

    override fun clearTemporaryData() {
        super.clearTemporaryData()
        albumsList.value = mutableListOf()
    }

    override fun onCleared() {
        super.onCleared()
        // clear composite requests
        compositeDisposable?.dispose()
    }
}
