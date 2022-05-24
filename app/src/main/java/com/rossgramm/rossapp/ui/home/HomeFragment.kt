package com.rossgramm.rossapp.ui.home

import FeedAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rossgramm.rossapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val home =  ViewModelProvider(this).get(HomeViewModel::class.java)
        home.loadPosts()

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val adapter = FeedAdapter()
        val postsView: RecyclerView = binding.postFeed
        postsView.layoutManager = LinearLayoutManager(context)
        postsView.adapter = adapter

        home.posts.observe(viewLifecycleOwner) {
            adapter.updatePosts(it)
            binding.postFeed.adapter?.notifyDataSetChanged()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}