package com.dmp.senya.ui.fragment

import androidx.fragment.app.Fragment
import com.dmp.senya.ui.MainActivity

abstract class BaseFragment: Fragment() {

    protected val navController by lazy {
        (activity as MainActivity).navController
    }
}