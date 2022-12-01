package com.riztech.ewallet.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.riztech.ewallet.R
import com.riztech.ewallet.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserLogin()
        viewModel.getAllTransactionByUsername()
        subscribeToObservable()
        setupUI()
    }

    private fun setupUI(){
        binding.tvTopUp.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToTopUpFragment()
            findNavController().navigate(action)
        }

        binding.tvTransfer.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToTransferFragment()
            findNavController().navigate(action)
        }

        binding.tvWithdraw.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragment2ToWithdrawFragment()
            findNavController().navigate(action)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.clearSharedPreverence()
            val action = HomeFragmentDirections.actionHomeFragment2ToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun subscribeToObservable(){
        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                it?.let {
                    binding.tvGreeting.text = "Hello, ${it.name}"
                    binding.tvBalanceValue.text = "${it.amount}"
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.transactions.collectLatest {
                binding.rvTransaction.apply {
                    adapter = TransactionAdapter(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}