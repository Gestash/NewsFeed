package com.gestash.newsfeed.ui.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gestash.newsfeed.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val selectedArticle = ArticleFragmentArgs.fromBundle(arguments!!).selectedArticle
        val viewModelFactory = ArticleViewModelFactory(selectedArticle)
        binding.articleViewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(ArticleViewModel::class.java)


        binding.copyButton.setOnClickListener {
            try {
                val clipboard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
                clipboard.text = selectedArticle.url
                Toast.makeText(requireContext(), "Link to article was copied", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Link to this article is not found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, selectedArticle.url)
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
        return binding.root
    }
}