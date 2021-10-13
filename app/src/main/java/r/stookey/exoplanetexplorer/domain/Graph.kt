package r.stookey.exoplanetexplorer.domain

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.ScatterDataSet
import r.stookey.exoplanetexplorer.util.DataSetUtil
import timber.log.Timber


class Graph (var title: String,
             private var listOfPlanets: List<Planet>,
             xValue: String?,
             yValue: String? ) {



   var scatterData = ScatterDataSet(emptyList(), "")
   var barData = BarDataSet(emptyList(), "")


    var isBar = false

    private val dataUtil = DataSetUtil()

   init {
       Timber.d("new Graph created: ${title}, number of planets in graph: ${listOfPlanets.size}")
       createDataSetForGraph()
       //Could probably be moved to a factory that when a graph with x and y values is created
       if (xValue != null && yValue != null){
           Timber.d("creating custom graph and dataset")
           val dataset = dataUtil.createCustomDistribution(listOfPlanets, "$xValue - $yValue Distribution",
               xValue, yValue)
           Timber.d("highest x value: ${dataset.xMax}")
           Timber.d("highest y value: ${dataset.yMax}")
           Timber.d("lowest x value: ${dataset.xMin}")
           Timber.d("lowest y value: ${dataset.yMin}")
           scatterData = dataset
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
        }
   }
}