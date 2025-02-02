package com.dimas.networkexercise.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.networkexercise.base.AppModule
import com.dimas.networkexercise.presentation.adapter.DiseaseAdapter
import com.dimas.networkexercise.databinding.FragmentHomeBinding
import com.dimas.networkexercise.domain.model.MachineDisease
import com.dimas.networkexercise.presentation.viewmodel.HomeViewModel
import com.dimas.networkexercise.utils.Error
import com.dimas.networkexercise.utils.Initiate
import com.dimas.networkexercise.utils.Loading
import com.dimas.networkexercise.utils.Success
import com.dimas.networkexercise.utils.observeIn

class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel> { AppModule.homeViewModelFactory }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var adapter: DiseaseAdapter? = null

    private var page = 1
    private var nextPage = 1


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            if (adapter == null) adapter = DiseaseAdapter(mutableListOf())
            listDisease.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = this@HomeFragment.adapter
            }

            swpHome.setOnRefreshListener {
                swpHome.isRefreshing = false
                reset()
            }
        }
        observer(view.context)
        getListMovie()
    }

    private fun reset() {
        page = 1
        nextPage = 1
        adapter?.clear()
        getListMovie()
    }

    private fun observer(context: Context) {
        homeViewModel.movie.observeIn(this) {
            when(it) {
                is Success -> showListDisease(it.data)
                is Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                is Loading -> showLoader(it.isLoading)
                is Initiate -> {}
            }
        }
    }

    private fun showLoader(isLoading: Boolean) {
        binding?.apply {
            pbHome.isVisible = isLoading
            if (page == 1) listDisease.isVisible = !isLoading
        }
    }

    private fun showListDisease(data: List<MachineDisease>) {
        adapter?.addAll(data)
        nextPage = page.plus(1)
    }

    private fun getListMovie() {
       homeViewModel.getDisease()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}