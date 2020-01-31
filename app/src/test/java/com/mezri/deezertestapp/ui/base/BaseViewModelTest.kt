package com.mezri.deezertestapp.ui.base

import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.model.Artist
import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseViewModelTest {

    val artist = Artist(
        1,
        "mezri",
        "picture"
    )
    val albumsList = listOf(
        Album(
            1,
            "title",
            "cover",
            artist
        )
    )

    @Before
    open fun setup() {
        // init mock objects
        MockitoAnnotations.initMocks(this)
    }
}