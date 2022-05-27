package com.rossgramm.rossapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rossgramm.rossapp.dashboard.data.AlbumItem
import com.rossgramm.rossapp.databinding.FragmentDashboardBinding
import com.rossgramm.rossapp.login.data.adapters.AlbumAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dashboardViewModel.loadAlbums()
     //   val album: ListView = binding.photoAlbum

      /*  dashboardViewModel.album.observe(viewLifecycleOwner) {
            val adapter = AlbumAdapter(container!!.context, it)
            album.adapter = adapter
            //binding.photoAlbum.adapter.notifyDataSetChanged()
        } */

       // val adapter = ArtistAdapter(container!!.context, )

        /*if (container != null) {
            val adapter = AlbumAdapter(container.context, getTestData())
            album.adapter = adapter
        }*/

        // пока убрал нужно грузить через модель
        //val textView: TextView = binding.textDashboard
        /*dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
