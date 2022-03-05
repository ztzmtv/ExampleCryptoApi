package com.example.mycryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mycryptoapp.R
import com.example.mycryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.mycryptoapp.di.DaggerApplicationComponent
import com.example.mycryptoapp.domain.CoinInfo
import com.example.mycryptoapp.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }
//    private val viewModel by lazy {
//        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
//    }

    private lateinit var viewModel: CoinViewModel

    private val component by lazy {
        (application as CoinApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        log("onCreate")

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isOnePaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromsymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromsymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
        log("onCreate - done")
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val TAG = "CoinPriceListAct_TAG"

        private fun log(string: String) {
            Log.d(TAG, string)
        }
    }
}