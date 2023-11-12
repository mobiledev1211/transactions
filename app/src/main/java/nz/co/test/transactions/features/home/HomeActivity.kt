package nz.co.test.transactions.features.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import nz.co.test.transactions.databinding.ActivityMainBinding
import nz.co.test.transactions.features.credits.CreditsFragment
import nz.co.test.transactions.features.transactions.TransactionsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewPager: ViewPager2 by lazy { binding.viewPager }
    private val bottomNavigationView: BottomNavigationView by lazy { binding.bottomNavigationView }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity: AppCompatActivity = this
        // init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init viewModel
        viewModel.init(
            activity = activity,
            listFragments = listOf(
                TransactionsFragment.newInstance(),
                CreditsFragment.newInstance(
                    title = "By Keegan Liew\nhttps://www.linkedin.com/in/keeganliew/",
                    description = "This is a simple application that will fetch a list of transactions from an api endpoint and display them in a list.  When a user selects a transaction they will be shown a second screen that will display a more detailed view of the transaction data.\nhttps://github.com/MobileChapter/recruit-android"
                ),
            ),
        )

        // init viewPager
        viewPager.adapter = viewModel.pagerAdapter
        viewPager.isUserInputEnabled = false
        viewPager.offscreenPageLimit = 1
        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.onPageSelected(position)
                }
            }
        )

        // init bottom navigation
        bottomNavigationView.setOnItemSelectedListener {
            viewModel.onNavigationItemSelected(it) { idx ->
                Log.d("TAG", "idx: $idx")
                viewPager.currentItem = idx
            }
        }
    }
}

