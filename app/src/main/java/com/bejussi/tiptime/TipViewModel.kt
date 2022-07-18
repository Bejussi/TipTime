package com.bejussi.tiptime

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipViewModel: ViewModel() {

    private val _tip = MutableLiveData(0.0)
    val tip: LiveData<Double>
        get() = _tip

    private var _total = MutableLiveData(0.0)
    val total: LiveData<Double>
        get() = _total

    private var _perPerson = MutableLiveData(0.0)
    val perPerson: LiveData<Double>
        get() = _perPerson

    private var roundUpTip: Boolean = true

    fun calculateTip(cost: Double, person: Double, tipPercentage: Double) {
        _tip.value = tipPercentage * cost

        if (roundUpTip) {
            _tip.value = kotlin.math.ceil(_tip.value!!)
        }

        _total.value = cost + _tip.value!!
        _perPerson.value = _total.value!! / person
    }

    fun roundUpTip(check: Boolean){
        roundUpTip = check
    }
}