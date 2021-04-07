package com.ldg.notdrunk.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.MVVMFragment
import com.ldg.notdrunk.databinding.IntroFragmentBinding
import com.ldg.notdrunk.main.MainActivity
import kotlinx.android.synthetic.main.intro_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroFragment : MVVMFragment<IntroViewModel,IntroFragmentBinding>(){

    //animation logo
    private val logoAnimation by lazy {
        AnimationUtils.loadAnimation(context,R.anim.logo_animation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectViewModel(IntroViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView(inflater, R.layout.intro_fragment,container)

        viewBinding.introViewModel=viewModel


        //animation
        viewModel.onLogoAnimation.observe(this.viewLifecycleOwner, Observer { animation->
                if(animation) {
                    viewBinding.ivLogo.animation=logoAnimation
                    viewBinding.ivLogo.animate()
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.startSettingApp(requireContext())
                    }
                }else{
                viewBinding.ivLogo.animation.cancel()
                viewBinding.ivLogo.clearAnimation()
                Intent(context,MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .let { startActivity(it) }
                }}
        )


        return viewBinding.root
    }



}