package com.mezri.deezertestapp.ui.base

import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.model.Artist
import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseViewModelTest {

    val artist = Artist(
        1,
        "mezri",
        "picture",
        "picture_small",
        "picture_medium",
        "picture_big",
        "picture_xl",
        "tracks",
        "type"
    )
    val albumsList = listOf(
        Album(
            1,
            "title",
            "link",
            "cover",
            "cover_small",
            "cover_medium",
            "cover_big",
            "cover_xl",
            12,
            "102938",
            "record",
            true,
            "tracks",
            true,
            123,
            artist,
            "type"
        )
    )

    @Before
    open fun setup() {
        // init mock objects
        MockitoAnnotations.initMocks(this)
    }
}