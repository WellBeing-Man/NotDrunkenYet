package com.ldg.notdrunk.main.drink

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.MVVMFragment
import com.ldg.notdrunk.base.ViewModelFactory
import com.ldg.notdrunk.databinding.FragmentDrinkBinding
import com.ldg.notdrunk.game.GameActivity
import com.ldg.notdrunk.main.MainActivity
import com.ldg.notdrunk.main.drink.callback.ChangeCupCallBack
import com.ldg.notdrunk.util.binding.cupNumber
import com.ldg.notdrunk.util.binding.currentCups


class DrinkFragment : MVVMFragment<DrinkViewModel,FragmentDrinkBinding>() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectViewModel(DrinkViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView(inflater, R.layout.fragment_drink, container)
        viewBinding.drinkViewModel=viewModel
        cupListSetting()

        drinkSelectSetting()


        saveButtonSetting()


        return viewBinding.root
    }


    //ui components set about save
    private fun saveButtonSetting() {
        viewModel.onClickDrinkButton.observe(this.viewLifecycleOwner, Observer { click ->
            if (click) {
//                viewModel.saveHistory();
//                viewModel.onClickDrinkButtonFinish()
                startActivityForResult(Intent(context, GameActivity::class.java),GameActivity.CODE_GAME_DONE)
            }
        })

    }

    //ui components set about drink
    private fun drinkSelectSetting() {

        //drink list observing
        viewModel.drinkList.observe(this.viewLifecycleOwner, Observer { it->
            val drink = it[viewModel.drinkIndex.value!!]

            if (drink != null) {
                Glide.with(this)
                    .load(drink.imageLink)
                    .override(600, 800)
                    .error(R.drawable.ic_home_black_24dp)
                    .placeholder(R.drawable.logo)
                    .into(viewBinding.ivDrinkPicture)

                viewBinding.tvDrinkKind.text = drink.drinkName
                viewBinding.tvAlcoholPer.text = drink.percentString()
                viewBinding.tvAlcoholDescription.text = drink.description
            }
        })


        //drink list index observing
        viewModel.drinkIndex.observe(this.viewLifecycleOwner, Observer { index ->
            val drink = viewModel.drinkList.value?.get(index)

            if (drink != null) {
                Glide.with(this)
                    .load(drink.imageLink)
                    .override(600, 850)
                    .error(R.drawable.ic_home_black_24dp)
                    .placeholder(R.drawable.ic_baseline_cloud_download_24)
                    .into(viewBinding.ivDrinkPicture)

                viewBinding.tvDrinkKind.text = drink.drinkName
                viewBinding.tvAlcoholPer.text = drink.percentString()
                viewBinding.tvAlcoholDescription.text = drink.description
            }
            })


        //drink select dialog fragment
        viewModel.onClickSelectDrink.observe(this.viewLifecycleOwner, Observer { clicked ->
            if (clicked) {
                findNavController().navigate(DrinkFragmentDirections.actionNavigationDrinkToSelectDrinkFragment())
                viewModel.onClickSelectFinish()
            }
        }
        )
    }

    //ui components set about cup
    private fun cupListSetting() {
        val adapter=  CupSelectAdapter(viewModel.changeCupCallBack)
        viewBinding.rvCupList.layoutManager =
            LinearLayoutManager(context).also { it.orientation = LinearLayoutManager.HORIZONTAL }
       viewBinding.rvCupList.adapter=adapter


        //live data of cup list observing
        viewModel.cupList.observe(this.viewLifecycleOwner, Observer { list->

            adapter.cupList=list
            adapter.notifyDataSetChanged()
            val cup = list[viewModel.cupIndex.value!!]
            viewBinding.tvCupName.text = cup.cupName
        })


        // Current selected cup observing
        viewModel.cupIndex.observe(this.viewLifecycleOwner, Observer { index ->
            if(viewModel.cupList.value !=null){
                val cup = viewModel.cupList.value!![index]
                viewBinding.tvCupName.currentCups(cup.cupName)
            }else
                viewBinding.tvCupName.currentCups("Cup")
        })


        //current cup number observing
        viewModel.cupNumber.observe(this.viewLifecycleOwner, Observer { number ->
                viewBinding.tvCupNumber.cupNumber(number)

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK)
            return
        when(requestCode){
            GameActivity.CODE_GAME_DONE->
                viewModel.saveHistory()


            else ->
                return
        }
    }
}