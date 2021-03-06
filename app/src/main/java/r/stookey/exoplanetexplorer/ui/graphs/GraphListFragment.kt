package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.viewmodels.GraphViewModel

//Displays list of graphs that can be selected, upon click navigates to GraphFragment which displays selected graph
//This page gets its info from GraphViewModel
@AndroidEntryPoint
class GraphListFragment() : Fragment() {

    companion object {
        fun newInstance() = GraphListFragment()
    }

    private val graphsListViewModel: GraphViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ExoplanetExplorerTheme {
                    Scaffold(
                        topBar = { GraphTopBar() },
                        content = { ListOfGraphs() },
                        drawerContent = {}
                    )
                }
            }
        }
    }

    @Composable
    fun GraphTopBar(){
        val topBarModifier = Modifier.height(72.dp)
        TopAppBar(topBarModifier) {
            Text("Graphs", Modifier.padding(16.dp), fontSize = 32.sp)
        }
    }

    @Composable
    fun ListOfGraphs(){
        val columnModifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()

        val rowModifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(48.dp)
            .border(2.dp, MaterialTheme.colors.secondary)
            .background(MaterialTheme.colors.primary)

        Column(columnModifier) {
            //Option for Custom Distributions
            Row(rowModifier.clickable {
                graphsListViewModel.onCustomGraphSelected()
                val action = GraphListFragmentDirections.viewGraph()
                findNavController().navigate(action)
            }){
                Text("Custom Distribution", Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.CenterVertically), fontSize = 24.sp)
            }
            //Options for Pre-Generated Plots
            graphsListViewModel.graphList.value.forEach { graph ->
                Row(rowModifier.clickable {
                    //Navigate to Fragment with graph data and display that graph
                    graphsListViewModel.onGraphSelected(graph)
                    val action = GraphListFragmentDirections.viewGraph()
                    findNavController().navigate(action)
                }){
                    Text(graph, Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.CenterVertically), fontSize = 24.sp)
                }
            }
        }
    }
}


