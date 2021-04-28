package com.ldg.notdrunk.game

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.MVVMFragment
import com.ldg.notdrunk.databinding.FragmentMoleGameBinding

class MoleGameFragment : MVVMFragment<MoleGameViewModel,FragmentMoleGameBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            connectViewModel(MoleGameViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            bindingView(inflater,R.layout.fragment_mole_game,container)
            viewBinding.modeGameViewModel=viewModel;
            viewBinding.gameBoard.moleGameCallBack=viewModel.moleGameCallBack


        //finish activity when game finish
            viewModel.onGameFinished.observe(this.viewLifecycleOwner, Observer { done->
                if(done){
                    viewModel.onFinishGameDone()
                    requireActivity().setResult(Activity.RESULT_OK)
                    requireActivity().finish()
                }
            })

        return viewBinding.root
    }


    override fun onDestroyView() {
        //destroy coroutine
        viewBinding.gameBoard.destroyJob()
        super.onDestroyView()
    }
}