package com.dmp.senya.arch

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmp.senya.data.Attraction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AttractionsViewModel : ViewModel() {

    private val repository = AttractionsRepository()

    // HomeFragment
    val attractionListLiveData = MutableLiveData<ArrayList<Attraction>>()

    // AttractionDetailFragment
    val selectedAttractionLiveData = MutableLiveData<Attraction>()

    // MainActivity
    val locationSelectedLiveData = MutableLiveData<Attraction>()

    fun init(context: Context) {
        viewModelScope.launch {
            delay(5_000)
            val attractionsList = repository.parseAttractions(context)
            attractionListLiveData.postValue(attractionsList)
        }
    }

    fun onAttractionSelected(attractionId: String) {
        val attraction = attractionListLiveData.value?.find {
            it.id == attractionId
        } ?: return

        selectedAttractionLiveData.postValue(attraction)
    }
}