package com.gestash.newsfeed.ui.home

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gestash.newsfeed.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var keyWord: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.newsList.adapter = HomeNewsFeedAdapter(HomeNewsFeedAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, {
            if (null != it) {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToArticleFragment(
                        it
                    )
                )
                viewModel.displayPropertyDetailsComplete()
            }
        })

        binding.keyWordSearch.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                binding.keyWordSearch.hideKeyboard()

                // сохраняем текст, введенный до нажатия Enter в переменную
                keyWord = binding.keyWordSearch.text.toString()
                viewModel.getNewsFromNet(keyWord)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        return binding.root
    }

    private fun View.hideKeyboard(){
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}