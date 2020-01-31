package com.mezri.deezertestapp.ui.albumdetails

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.mezri.deezertestapp.R
import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.databinding.AlbumDetailsFragmentBinding
import com.mezri.deezertestapp.ui.base.BaseFragment
import com.mezri.deezertestapp.utils.glide.load
import kotlinx.android.synthetic.main.album_details_fragment.*


class AlbumDetailsFragment : BaseFragment(), View.OnClickListener {

    // fragment view model
    private lateinit var fragmentViewModel: AlbumDetailsFragmentViewModel

    companion object {
        private const val ALBUM_KEY = "album"
        fun newInstance(album: Album): AlbumDetailsFragment {
            val fragment = AlbumDetailsFragment()

            val bundle = Bundle()
            bundle.putParcelable(ALBUM_KEY, album)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // shared elements configuration
        postponeEnterTransition()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            enterTransition =
                TransitionInflater.from(context)
                    .inflateTransition(android.R.transition.no_transition)
        }

        // init view model
        fragmentViewModel =
            ViewModelProviders.of(this).get(AlbumDetailsFragmentViewModel::class.java)
        fragmentViewModel.initAlbum(
            arguments?.getParcelable(ALBUM_KEY) ?: throw Exception("Album not found")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = DataBindingUtil.inflate<AlbumDetailsFragmentBinding>(
            inflater,
            R.layout.album_details_fragment, container, false
        )
        fragmentBinding.viewModel = fragmentViewModel
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // shared elements configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgAlbumCover.transitionName = fragmentViewModel.album.id.toString()
        }

        // load album cover
        imgAlbumCover.load(
            fragmentViewModel.album.cover,
            true
        ) { startPostponedEnterTransition() }

        // init UI buttons
        initUIButtons()
        // init action bar
        initActionBar()
    }

    /**
     * Init UI buttons
     */
    private fun initUIButtons() {
        btnShare.setOnClickListener(this)
        btnFavorites.setOnClickListener(this)
        btnPlayAlbum.setOnClickListener(this)
    }

    /**
     * Init collapse app bar
     */
    private fun initActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).title = ""
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        // Enable option menu
        setHasOptionsMenu(true)
    }

    override fun initObservers() {
        // TODO
    }

    override fun removeObservers() {
        // TODO
    }

    override fun getFragmentViewModel() = fragmentViewModel

    /**
     * Handle option menu click
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Handle UI buttons click
     */
    override fun onClick(view: View) {
        Toast.makeText(requireContext(), R.string.todo, Toast.LENGTH_SHORT).show()
    }
}