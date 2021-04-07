package com.ldg.notdrunk.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class MVVMFragment<V:ViewModel,B:ViewDataBinding> :Fragment(){
      lateinit var viewBinding: B
      lateinit var viewModel: V

      //connecting view model method
     fun connectViewModel(modelClass: Class<V>) {
        viewModel=ViewModelProvider(requireActivity(),ViewModelFactory(application = requireActivity().application)).get(modelClass)
    }
    //binding view methid
     fun bindingView(inflater:LayoutInflater, redId:Int, container: ViewGroup?) {
        viewBinding=DataBindingUtil.inflate(inflater,redId,container,false)
    }

}