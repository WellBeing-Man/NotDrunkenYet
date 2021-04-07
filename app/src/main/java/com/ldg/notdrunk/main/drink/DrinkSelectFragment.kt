package com.ldg.notdrunk.main.drink

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ldg.notdrunk.R
import com.ldg.notdrunk.base.MVVMDialogFragment
import com.ldg.notdrunk.database.Drink
import com.ldg.notdrunk.databinding.SelectDrinkFragmentBinding
import com.ldg.notdrunk.generated.callback.OnClickListener
import com.ldg.notdrunk.util.clear

class DrinkSelectFragment : MVVMDialogFragment<DrinkViewModel,SelectDrinkFragmentBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectViewModel(DrinkViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView(inflater, R.layout.select_drink_fragment,container);

        viewBinding.drinkViewModel=viewModel


        val adapter= MixDrinkItemSpinnerAdapter(listOf<Drink>(),layoutInflater)
        viewBinding.tvLeftChooseDrink.adapter=adapter
        viewBinding.tvRightChooseDrink.adapter=adapter


        viewModel.drinkList.observe(this.viewLifecycleOwner, Observer { list->
            val drinkAdapter=DrinkSelectAdapter(list, viewModel.changeDrinkCallBack)

            viewBinding.rvDrinkList.layoutManager=GridLayoutManager(requireContext(),list.size)
            viewBinding.rvDrinkList.adapter=drinkAdapter


            adapter.drinkList=list
            adapter.notifyDataSetChanged()

        })

        val clickListener= object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var mixDrinkName = viewBinding.etInputMixDrink.text.toString()
                var drinkRightRatio = viewBinding.etRightChooseDrinkPer.text.toString()
                var drinkLeftRatio = viewBinding.etLeftChooseDrinkPer.text.toString()
                if (mixDrinkName != "" && drinkRightRatio != "" && drinkLeftRatio != "") {
                    viewModel.addNewMixedDrink(
                        viewBinding.etInputMixDrink.text.toString(),
                        drinkRightRatio,
                        drinkLeftRatio
                    )
                    viewBinding.etInputMixDrink.clear()
                    viewBinding.etRightChooseDrinkPer.clear()
                    viewBinding.etLeftChooseDrinkPer.clear()
                }else {
                    Toast.makeText(context, "빈 칸을 모두 채워야 합니다", Toast.LENGTH_SHORT).show()
                }
                viewModel.onMixDrink()
            }


        }
        viewModel.onMixDrink.observe(this.viewLifecycleOwner, Observer { mix ->
            if (mix) {
                viewBinding.clMixLayout.visibility = View.VISIBLE
                viewBinding.btMixDrink.setOnClickListener(clickListener)
                viewBinding.btMixDrink.text=getText(R.string.drink_05)
            } else {
                viewBinding.clMixLayout.visibility = View.GONE
                viewBinding.btMixDrink.setOnClickListener{view ->
                    viewModel.onMixDrink()
                }
                val imm =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(viewBinding.etInputMixDrink.windowToken, 0)
                viewBinding.btMixDrink.text = getText(R.string.drink_03)
            }
        })


        viewBinding.tvLeftChooseDrink.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
               viewModel.setLeftMixDrinkIndex(index)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
               viewModel.setLeftMixDrinkIndex(0)
            }

        }

        viewBinding.tvRightChooseDrink.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
                viewModel.setRightMixDrinkIndex(index)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.setRightMixDrinkIndex(0)
            }
        }





        return viewBinding.root
    }
}