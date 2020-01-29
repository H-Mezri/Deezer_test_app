package com.mezri.deezertestapp.ui.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.ui.main.AlbumRecyclerAdapter
import org.hamcrest.Matchers.*

class RecyclerViewItemAssertion(private val title: String, private val contains: Boolean) :
    ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter as AlbumRecyclerAdapter
        val adapterList = adapter.albumList

        if (contains) {
            assertThat((adapterList[0] as Album).title, containsString(title))
        } else {
            assertThat(
                (adapterList[0] as Album).title,
                `is`(not(containsString(title)))
            )
        }
    }
}