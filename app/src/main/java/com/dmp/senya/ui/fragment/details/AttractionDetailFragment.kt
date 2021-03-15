package com.dmp.senya.ui.fragment.details

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.dmp.senya.R
import com.dmp.senya.databinding.FragmentActtractionDetailBinding
import com.dmp.senya.ui.fragment.BaseFragment

class AttractionDetailFragment : BaseFragment() {

    private var _binding: FragmentActtractionDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActtractionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.selectedAttractionLiveData.observe(viewLifecycleOwner) { attraction ->
            binding.titleTextView.text = attraction.title
            binding.headerEpoxyRecyclerView.setControllerAndBuildModels(
                HeaderEpoxyController(
                    attraction.image_urls
                )
            )
            LinearSnapHelper().attachToRecyclerView(binding.headerEpoxyRecyclerView)
            binding.indicator.attachToRecyclerView(binding.headerEpoxyRecyclerView)

            var isGridMode: Boolean = binding.contentEpoxyRecyclerView.layoutManager is GridLayoutManager
            val contentEpoxyController = ContentEpoxyController(attraction)
            contentEpoxyController.isGridMode = isGridMode
            contentEpoxyController.onChangeLayoutCallback = {
                if (isGridMode) {
                    binding.contentEpoxyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                } else {
                    binding.contentEpoxyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                }

                isGridMode = !isGridMode
                contentEpoxyController.isGridMode = isGridMode
                contentEpoxyController.requestModelBuild()
            }

            binding.contentEpoxyRecyclerView.setControllerAndBuildModels(contentEpoxyController)
//            binding.numberOfFactsTextView.setOnClickListener {
//                val stringBuilder = StringBuilder("")
//                attraction.facts.forEach {
//                    stringBuilder.append("\u2022 $it")
//                    stringBuilder.append("\n\n")
//                }
//                val message = stringBuilder.toString()
//                    .substring(0, stringBuilder.toString().lastIndexOf("\n\n"))
//
//                AlertDialog.Builder(requireContext(), R.style.MyDialog)
//                    .setTitle("${attraction.title} Facts")
//                    .setMessage(message)
//                    .setPositiveButton("Ok") { dialog, which ->
//                        // run your code
//                    }
//                    .setNegativeButton("No!") { dialog, which ->
//                        // run negative code
//                    }
//                    .show()
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_attraction_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemLocation -> {
                val attraction = activityViewModel.selectedAttractionLiveData.value ?: return true
                activityViewModel.locationSelectedLiveData.postValue(attraction)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}