package com.dmp.senya.arch

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmp.senya.data.Attraction

class AttractionsViewModel : ViewModel() {

    private val repository = AttractionsRepository()

    // HomeFragment
    val attractionListLiveData = MutableLiveData<List<Attraction>>()

    // AttractionDetailFragment
    val selectedAttractionLiveData = MutableLiveData<Attraction>()

    // MainActivity
    val locationSelectedLiveData = MutableLiveData<Attraction>()

    fun init(context: Context) {
        val attractionsList = repository.parseAttractions(context)
        attractionListLiveData.postValue(attractionsList)
    }

    fun onAttractionSelected(attractionId: String) {
        val attraction = attractionListLiveData.value?.find {
            it.id == attractionId
        } ?: return

        selectedAttractionLiveData.postValue(attraction)
    }
}