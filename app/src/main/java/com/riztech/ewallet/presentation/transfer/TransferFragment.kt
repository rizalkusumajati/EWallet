package com.riztech.ewallet.presentation.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.riztech.ewallet.R
import com.riztech.ewallet.databinding.FragmentTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [TransferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    val binding get() = _binding!!

    private val viewModel: TransferViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)
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
        binding.btnTransfer.setOnClickListener {
            val receiver = binding.etReceiver.text.toString()
            val amount = binding.etAmount.text.toString().toLongOrNull() ?: 0
            viewModel.transferTransaction(
                receiver = receiver,
                amount = amount,
                createdAt = System.currentTimeMillis()/1000
            )
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
                        binding.tvError.text = "Not Enaugh Balance"
                    }
                    "error_receiver" -> {
                        binding.tvError.text = "Receiver Not Exist"
                    }
                    "error_receiver_blank" -> {
                        binding.tvError.text = "Receiver cannot be blank"
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