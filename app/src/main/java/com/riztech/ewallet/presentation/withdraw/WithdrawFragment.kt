package com.riztech.ewallet.presentation.withdraw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.riztech.ewallet.R
import com.riztech.ewallet.databinding.FragmentWithdrawBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [WithdrawFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WithdrawFragment : Fragment() {

    private var _binding: FragmentWithdrawBinding? = null
    val binding get() = _binding!!

    private val viewModel: WithdrawViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWithdrawBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserLogin()


        subscribeToObservable()
        setupUI()
    }

    private fun setupUI(){
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnWithdraw.setOnClickListener {
            val amount = binding.etAmount.text.toString().toLongOrNull() ?: 0
            viewModel.withdrawTransaction(amount = amount, createdAt = System.currentTimeMillis()/1000)
        }
    }

    private fun subscribeToObservable() {
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
                    "error" -> {
                        binding.tvError.text = "Not Enaugh Balance"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}