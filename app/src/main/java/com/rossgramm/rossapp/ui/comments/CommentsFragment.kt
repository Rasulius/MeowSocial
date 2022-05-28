package com.rossgramm.rossapp.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rossgramm.rossapp.R
import com.rossgramm.rossapp.comments.adapter.CommentsAdapter
import com.rossgramm.rossapp.databinding.FragmentCommentsBinding


class CommentsFragment: Fragment() {

    private var _binding: FragmentCommentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun onBackLayoutPress(){
        val backLayout = binding.commentsBlockNavigateBack
        backLayout.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.navigation_home)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Здесь заполняем
        val commentsProvider =  ViewModelProvider(this).get(CommentsViewModel::class.java)
        commentsProvider.loadComments()
        val adapter = CommentsAdapter()
        val commentsView: RecyclerView = binding.commentsFeed
        commentsView.layoutManager = LinearLayoutManager(context)
        commentsView.adapter = adapter


        commentsProvider.comments.observe(viewLifecycleOwner) {
            adapter.updateComments(it)
            binding.commentsFeed.adapter?.notifyDataSetChanged()
        }
        // обрабатываем возврат на главную страницу
        onBackLayoutPress()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}