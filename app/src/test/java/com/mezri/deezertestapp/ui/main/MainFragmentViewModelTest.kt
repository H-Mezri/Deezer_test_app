package com.mezri.deezertestapp.ui.main

import android.os.Build
import com.mezri.deezertestapp.data.errors.AppMessages
import com.mezri.deezertestapp.data.repository.Repository
import com.mezri.deezertestapp.ui.base.BaseViewModelTest
import com.mezri.deezertestapp.utils.schedulers.TrampolineSchedulerProvider
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
class MainFragmentViewModelTest : BaseViewModelTest() {

    // View model to test
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    @Mock
    lateinit var repository: Repository

    override fun setup() {
        super.setup()
        // init view model
        mainFragmentViewModel = MainFragmentViewModel()
        mainFragmentViewModel.initRepository(repository, TrampolineSchedulerProvider())
    }

    @Test
    fun testGetAlbums_OK() {
        // given
        `when`(repository.getAlbums(0)).thenReturn(
            Single.just(albumsList)
        )
        assert(mainFragmentViewModel.albumsListCache.isEmpty())

        // when
        mainFragmentViewModel.getAlbums()

        // then
        assert(mainFragmentViewModel.albumsListCache.size == 1 && mainFragmentViewModel.albumsListCache[0] == albumsList[0])
    }

    @Test
    fun testGetAlbums_KO_NETWORK_ERROR() {
        // given
        val throwable = Throwable()
        `when`(repository.getAlbums(0)).thenReturn(Single.error(throwable))
        assert(mainFragmentViewModel.albumsListCache.isEmpty())

        // when
        mainFragmentViewModel.getAlbums()

        // then
        assert(mainFragmentViewModel.albumsListCache.isEmpty())
        assert(
            mainFragmentViewModel.informationToShow.value!! == AppMessages.NETWORK_ERROR.getAppError(
                throwable
            )
        )
    }

    @Test
    fun testGetAlbums_KO_ALBUMS_NOT_FOUND() {
        // given
        `when`(repository.getAlbums(0)).thenReturn(
            Single.just(listOf())
        )
        assert(mainFragmentViewModel.albumsListCache.isEmpty())

        // when
        mainFragmentViewModel.getAlbums()

        // then
        assert(mainFragmentViewModel.albumsListCache.isEmpty())
        assert(mainFragmentViewModel.informationToShow.value!! == AppMessages.ALBUMS_NOT_FOUND.getAppError())
    }
}