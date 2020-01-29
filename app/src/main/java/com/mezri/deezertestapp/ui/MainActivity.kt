package com.mezri.deezertestapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.mezri.deezertestapp.R
import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.ui.albumdetails.AlbumDetailsFragment
import com.mezri.deezertestapp.ui.main.MainFragment


class MainActivity : AppCompatActivity(), MainActivityInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MainFragment.newInstance(),
                    MainFragment::class.java.simpleName
                )
                .commitNow()
        }
    }

    override fun showAlbumDetailsFragment(view: View, album: Album) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // get new instance of fragment
        val fragment = AlbumDetailsFragment.newInstance(album)

        // add shared element to fragment transaction
        fragmentTransaction.addSharedElement(view, ViewCompat.getTransitionName(view)!!)

        fragmentTransaction.replace(
            R.id.container,
            fragment,
            AlbumDetailsFragment::class.java.simpleName
        )

        fragmentTransaction.addToBackStack(AlbumDetailsFragment::class.java.simpleName)

        fragmentTransaction.commit()
    }


}
