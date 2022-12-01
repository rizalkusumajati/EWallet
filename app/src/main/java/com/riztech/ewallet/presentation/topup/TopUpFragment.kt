package com.riztech.ewallet.presentation.topup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.riztech.ewallet.R
import com.riztech.ewallet.databinding.FragmentHomeBinding
import com.riztech.ewallet.databinding.FragmentTopUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


/**
 * A simple [Fragment] subclass.
 * Use the [TopUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TopUpFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentTopUpBinding? = null
    val binding get() = _binding!!

    private val viewModel: TopUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserLogin()
        subscribeToObservable()
        setupUI()
    }

    private fun setupUI(){
        binding.btnTopUp.setOnClickListener {
            val amount = binding.etAmount.text.toString().toLongOrNull() ?: 0
            viewModel.topUpTransaction(amount, createdAt = System.currentTimeMillis()/1000)
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun subscribeToObservable(){
        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                it?.let {
                    binding.tvBalanceValue.text = "${it.amount}"
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.collectLatest {
                when(it){
                    "home" -> {
                        findNavController().navigateUp()
                    }
                    "error_amount" -> {
                        binding.tvError.text = "Not Valid Balance"
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}