package com.dmp.senya.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.dmp.senya.R
import com.dmp.senya.data.Attraction
import com.dmp.senya.databinding.FragmentActtractionDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class AttractionDetailFragment : BaseFragment() {

    private var _binding: FragmentActtractionDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: AttractionDetailFragmentArgs by navArgs()
    private val attraction: Attraction by lazy {
        attractions.find { it.id == safeArgs.attractionId }!!
    }

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

        binding.titleTextView.text = attraction.title
        binding.descriptionTextView.text = attraction.description
        Picasso.get().load(attraction.image_url).into(binding.headerImageView)
        binding.monthsToVisitTextView.text = attraction.months_to_visit

        binding.numberOfFactsTextView.text = "${attraction.facts.size} facts"
        binding.numberOfFactsTextView.setOnClickListener {
            val stringBuilder = StringBuilder("")
            attraction.facts.forEach {
                stringBuilder.append("\u2022 $it")
                stringBuilder.append("\n\n")
            }
            val message = stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf("\n\n"))

            AlertDialog.Builder(requireContext(), R.style.MyDialog)
                .setTitle("${attraction.title} Facts")
                .setMessage(message)
                .setPositiveButton("Ok") { dialog, which ->
                    // run your code
                }
                .setNegativeButton("No!") { dialog, which ->
                    // run negative code
                }
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_attraction_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuItemLocation -> {
                val uri = Uri.parse("geo:${attraction.location.latitude},${attraction.location.longitude}?z=9&q=${attraction.title}")
                val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
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