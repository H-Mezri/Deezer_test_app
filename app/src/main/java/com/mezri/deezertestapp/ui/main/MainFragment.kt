package com.mezri.deezertestapp.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.mezri.deezertestapp.R
import com.mezri.deezertestapp.data.model.Album
import com.mezri.deezertestapp.data.repository.RepositoryImpl
import com.mezri.deezertestapp.ui.MainActivityInterface
import com.mezri.deezertestapp.ui.base.BaseFragment
import com.mezri.deezertestapp.utils.schedulers.SchedulerProvider
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : BaseFragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    // fragment view model
    private lateinit var fragmentViewModel: MainFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementReturnTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            exitTransition =
                TransitionInflater.from(context)
                    .inflateTransition(android.R.transition.no_transition)
        }

        // init view model
        fragmentViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)

        // set repository
        fragmentViewModel.initRepository(RepositoryImpl(), SchedulerProvider())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init recycler view list
        initRecyclerViewList()
    }

    override fun onResume() {
        super.onResume()
        if (fragmentViewModel.albumsListCache.size == 0) {
            // load albums if list is empty
            loadAlbums()
        }
    }

    private fun initRecyclerViewList() {
        // Init recycler view
        recyclerAlbumsList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)

            adapter = AlbumRecyclerAdapter(
                mutableListOf(),
                object :
                    AlbumRecyclerAdapter.OnItemClickListener {
                    override fun onClick(view: View, album: Album) {
                        (requireActivity() as MainActivityInterface).showAlbumDetailsFragment(
                            view,
                            album
                        )
                    }
                })
        }

        // set recycler view on scroll listener to load albums when scrolling
        recyclerAlbumsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // if scrolling up return
                if (dy <= 0) {
                    return
                }

                val recyclerGridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItem = recyclerGridLayoutManager.findLastVisibleItemPosition()
                if (!fragmentViewModel.isLoadingAlbums && recyclerGridLayoutManager.itemCount <= lastVisibleItem + 1) {
                    // if last item on the list is visible then load more albums
                    loadAlbums(true)
                }
            }
        })
    }

    /**
     * Update UI and start loading albums from view model
     */
    private fun loadAlbums(isLoadMore: Boolean = false) {
        // if there is no request sent
        if (!(recyclerAlbumsList.adapter as AlbumRecyclerAdapter).isLoadingData()) {
            if (isLoadMore) {
                (recyclerAlbumsList.adapter as AlbumRecyclerAdapter).addLoadingView()
            }
            fragmentViewModel.getAlbums()
        }
    }

    /**
     * Init View observers
     */
    override fun initObservers() {
        // observer for app message and errors
        fragmentViewModel.informationToShow.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Snackbar.make(
                    rootLayout,
                    getString(it.messageId),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        // observe no album loaded
        fragmentViewModel.isNoAlbumLoaded.observe(viewLifecycleOwner, Observer { isNoAlbumLoaded ->
            if (isNoAlbumLoaded) {
                lytNoMusicFound.visibility = View.VISIBLE
                btnLoadAlbums.setOnClickListener { loadAlbums() }
            } else {
                lytNoMusicFound.visibility = View.GONE
            }
        })

        // observe app loading albums
        fragmentViewModel.isFirstRequestLoadingAlbums.observe(
            viewLifecycleOwner,
            Observer { isFirstRequestLoadingAlbums ->
                if (isFirstRequestLoadingAlbums) {
                    progressBarLoadingAlbums.visibility = View.VISIBLE
                } else {
                    progressBarLoadingAlbums.visibility = View.GONE
                }
            })

        // observe for view model albums list
        fragmentViewModel.albumsList.observe(
            viewLifecycleOwner,
            Observer { newAlbumsList ->
                when {
                    newAlbumsList.isNotEmpty() -> {
                        // in case new album loaded
                        (recyclerAlbumsList.adapter as AlbumRecyclerAdapter).onNewAlbumsListLoaded(
                            newAlbumsList
                        )
                    }
                    recyclerAlbumsList.adapter?.itemCount == 0 -> {
                        // in case reload album from cache
                        (recyclerAlbumsList.adapter as AlbumRecyclerAdapter).onNewAlbumsListLoaded(
                            fragmentViewModel.albumsListCache.toMutableList()
                        )
                    }
                    else -> {
                        // in case no more album to load
                        (recyclerAlbumsList.adapter as AlbumRecyclerAdapter).onNewAlbumsListLoaded(
                            mutableListOf()
                        )
                    }
                }
            })
    }

    /**
     * UnSubscribing from observers
     */
    override fun removeObservers() {
        // clean observers
        if (fragmentViewModel.isNoAlbumLoaded.hasObservers()) {
            fragmentViewModel.isNoAlbumLoaded.removeObservers(viewLifecycleOwner)
        }
        if (fragmentViewModel.isFirstRequestLoadingAlbums.hasObservers()) {
            fragmentViewModel.isFirstRequestLoadingAlbums.removeObservers(viewLifecycleOwner)
        }
        if (fragmentViewModel.albumsList.hasObservers()) {
            fragmentViewModel.albumsList.removeObservers(viewLifecycleOwner)
        }
        if (fragmentViewModel.informationToShow.hasObservers()) {
            fragmentViewModel.informationToShow.removeObservers(viewLifecycleOwner)
        }
    }

    override fun getFragmentViewModel() = fragmentViewModel
}
