package r.stookey.exoplanetexplorer.ui.tester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.databinding.FragmentTestBinding

@AndroidEntryPoint
class TestFragment : Fragment() {


    private lateinit var testerViewModel: TestViewModel
    private var _binding: FragmentTestBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testerViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        
    }

}