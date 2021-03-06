package r.stookey.exoplanetexplorer.domain

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.ScatterDataSet
import r.stookey.exoplanetexplorer.util.DataSetUtil
import timber.log.Timber
import javax.inject.Inject


class Graph (var title: String,
             private var listOfPlanets: List<Planet>,
             xValue: String?,
             yValue: String?,
) {


   var scatterData = ScatterDataSet(emptyList(), "")
   var barData = BarDataSet(emptyList(), "")
   var isBar = false

   private val dataUtil = DataSetUtil()


   init {
       Timber.d("new Graph created: ${title}, number of planets in graph: ${listOfPlanets.size}")
       createDataSetForGraph()
       if (xValue != null && yValue != null){
           Timber.d("creating custom graph and dataset")
           val customDataSet = dataUtil.createCustomDistribution(listOfPlanets, "$xValue - $yValue Distribution",
               xValue, yValue)
           Timber.d("highest x value: ${customDataSet.xMax}")
           Timber.d("lowest x value: ${customDataSet.xMin}")
           Timber.d("highest y value: ${customDataSet.yMax}")
           Timber.d("lowest y value: ${customDataSet.yMin}")
           scatterData = customDataSet
           title = scatterData.label
       }
   }


   private fun createDataSetForGraph(){
       when(title){
            "Density - Mass" -> {
                val densityMassDataSet = dataUtil.densityMassDistributionDataSet(listOfPlanets, title)
                scatterData = densityMassDataSet
            }
           "Density - Radius" -> {
               val densityRadiusDataSet = dataUtil.densityRadiusDistributionDataSet(listOfPlanets, title)
               scatterData = densityRadiusDataSet
           }
           "Mass - Period" -> {
               val massPeriodDataSet = dataUtil.massPeriodDistributionDataSet(listOfPlanets, title)
               scatterData = massPeriodDataSet
           }
           "Radius - Period" -> {
               val radiusPeriodDataSSet = dataUtil.radiusPeriodDistributionDataSet(listOfPlanets, title)
               scatterData = radiusPeriodDataSSet
           }
           "Eccentricity - Period" -> {
               val eccentricityPeriodDataSet = dataUtil.eccentricityPeriodDistributionDataSet(listOfPlanets, title)
               scatterData = eccentricityPeriodDataSet
           }
           "EarthMass - EarthRadius" -> {
               val earthMassEarthRadiusDataSet = dataUtil.earthMassRadiusDistributionDataSet(listOfPlanets, title)
               scatterData = earthMassEarthRadiusDataSet
           }
           "Detections Per Year" -> {
               val detectionsPerYearDataSet = dataUtil.createDiscoveryYearDataSet(listOfPlanets, title)
               barData = detectionsPerYearDataSet
               isBar = true
           }
           /*"Detections Per Discovery Method" -> {
               val planetsPerPlanetarySystemDataSet = dataUtil.createDiscoveryMethodDataSet(listOfPlanets, title)
               barData = planetsPerPlanetarySystemDataSet
               isBar = true
           }*/
        }
   }
}