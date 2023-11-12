package nz.co.test.transactions.features.home

import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import nz.co.test.transactions.R
import javax.inject.Inject


class HomeViewModel @Inject constructor(
) : ViewModel() {


    companion object {
        val TAG: String = HomeViewModel::class.java.simpleName
    }

    var pagerAdapter: HomeViewPagerAdapter? = null;
    var isLoading: Boolean = false
    private var _pagerIndex: Int = 0

    fun onNavigationItemSelected(item: MenuItem, setPage: (Int) -> Unit): Boolean {
        Log.d(TAG, "onNavigationItemSelected item: $item")
        var page: Int? = null
        when (item.itemId) {
            R.id.menu_transactions_list -> page = 0
            R.id.menu_transaction_detail -> page = 1
        }
        Log.d(TAG, "page: $page")
        page?.let {
            setPage(it)
            return true
        }
        return false
    }


    fun onPageSelected(position: Int) {
        Log.d(TAG, "position: $position")
        _pagerIndex = position
    }

    fun init(activity: AppCompatActivity, listFragments: List<Fragment>) {
        pagerAdapter = HomeViewPagerAdapter(
            parent = activity,
            listFragments = listFragments
        )
    }
}

