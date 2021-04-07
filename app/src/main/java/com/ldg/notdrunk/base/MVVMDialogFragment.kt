package com.ldg.notdrunk.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class MVVMDialogFragment<V:ViewModel,B:ViewDataBinding> : BottomSheetDialogFragment(){
     lateinit var viewBinding: B
     lateinit var viewModel: V

    fun connectViewModel(modelClass: Class<V>) {
        viewModel=ViewModelProvider(requireActivity()).get(modelClass)
    }

    fun bindingView(inflater:LayoutInflater, redId:Int, container: ViewGroup?) {
        viewBinding=DataBindingUtil.inflate(inflater,redId,container,false)
    }


}