package com.dmp.senya.ui.fragment.details

import com.airbnb.epoxy.EpoxyController
import com.dmp.senya.R
import com.dmp.senya.data.Attraction
import com.dmp.senya.databinding.ModelDescriptionBinding
import com.dmp.senya.databinding.ModelFactBinding
import com.dmp.senya.databinding.ModelFactsHeaderBinding
import com.dmp.senya.databinding.ModelMonthsToVisitBinding
import com.dmp.senya.ui.epoxy.ViewBindingKotlinModel

class ContentEpoxyController(
    private val attraction: Attraction
) : EpoxyController() {

    var isGridMode: Boolean = false
    lateinit var onChangeLayoutCallback: () -> Unit

    override fun buildModels() {

        DescriptionEpoxyModel(attraction.description).id("description").addTo(this)

        FactsHeaderEpoxyModel(
            "${attraction.facts.size} Facts",
            isGridMode,
            onChangeLayoutCallback
        ).id("facts_header").addTo(this)

        attraction.facts.forEachIndexed { index, fact ->
            FactEpoxyModel(fact).id("fact_$index").addTo(this)
        }

        MonthsToVisitEpoxyModel(attraction.months_to_visit).id("months_to_visit").addTo(this)
    }

    data class MonthsToVisitEpoxyModel(
        val monthsToVisit: String
    ) : ViewBindingKotlinModel<ModelMonthsToVisitBinding>(R.layout.model_months_to_visit) {

        override fun ModelMonthsToVisitBinding.bind() {
            textView.text = monthsToVisit
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class DescriptionEpoxyModel(
        val description: String
    ) : ViewBindingKotlinModel<ModelDescriptionBinding>(R.layout.model_description) {

        override fun ModelDescriptionBinding.bind() {
            textView.text = description
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class FactsHeaderEpoxyModel(
        val factsText: String,
        val isGridMode: Boolean,
        val onChangeLayoutCallback: () -> Unit
    ) : ViewBindingKotlinModel<ModelFactsHeaderBinding>(R.layout.model_facts_header) {

        override fun ModelFactsHeaderBinding.bind() {
            factsTextView.text = factsText
            toggleImageView.setOnClickListener {
                onChangeLayoutCallback.invoke()
            }

            toggleImageView.setImageResource(if (isGridMode) R.drawable.ic_list_view_24 else R.drawable.ic_grid_view_24)
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }

    data class FactEpoxyModel(
        val fact: String
    ) : ViewBindingKotlinModel<ModelFactBinding>(R.layout.model_fact) {

        override fun ModelFactBinding.bind() {
            textView.text = fact
        }
    }
}