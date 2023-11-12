package nz.co.test.transactions.features.credits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nz.co.test.transactions.databinding.FragmentCreditsBinding

private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"

/**
 * A simple [Fragment] subclass.
 * Use the [CreditsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreditsFragment : Fragment() {
    private lateinit var binding: FragmentCreditsBinding
    private var title: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreditsBinding.inflate(inflater)

        binding.textViewTitle.text = title
        binding.textViewDescription.text = description

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param title Parameter 1.
         * @param description Parameter 2.
         * @return A new instance of fragment CreditsFragment.
         */
        @JvmStatic
        fun newInstance(title: String, description: String) =
            CreditsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }
}