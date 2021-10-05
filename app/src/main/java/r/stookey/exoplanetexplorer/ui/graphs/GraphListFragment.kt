package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.DataSetUtil
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart


@AndroidEntryPoint
class GraphListFragment() : Fragment() {

    companion object {
        fun newInstance() = GraphListFragment()
    }

    private val graphsListViewModel: GraphListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ExoplanetExplorerTheme {
                    Scaffold(
                        topBar = { GraphTopBar() },
                        content = { GraphContent() },
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
            Text("Graphs")
        }
    }

    @Composable
    fun GraphContent(){
        ListOfGraphs()
    }

    @Composable
    fun ListOfGraphs(){
        val columnModifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.Gray)

        val rowModifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)

        Column(columnModifier) {
            graphsListViewModel.graphList.value.forEach { graph ->
                Row(rowModifier.clickable {
                    //Navigate to Fragment with graph data and display that graph
                    graphsListViewModel.onGraphSelected(graph)

                }){
                    Text(graph.title, fontSize = 24.sp)
                }
            }
        }
    }
}


