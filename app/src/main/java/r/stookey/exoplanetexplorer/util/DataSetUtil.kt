package r.stookey.exoplanetexplorer.util

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow


val scientificFormat = DecimalFormat("0.0E0")

fun unscaleValues(cbr: Double): Float {
    val calcVal = 10.0.pow(cbr)
    return calcVal.toFloat()
}

//Used for scaling values to Logarithmic
fun scaleValues(number: Double): Float {
    return log10(number).toFloat()
}

class DataSetUtil {

    //Scatter Charts
    fun massPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryMassJupiter != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    scaleValues(planet.planetaryMassJupiter)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        val dataSet = ScatterDataSet(planetEntryList, label)
        return dataSet
    }

    fun radiusPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryRadiusEarth != null && planet.planetaryOrbitPeriod != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    scaleValues(planet.planetaryRadiusEarth)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        val dataset = ScatterDataSet(planetEntryList, label)
        return dataset
    }

    fun densityRadiusDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetDensity != null && planet.planetaryRadiusEarth != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryRadiusEarth),
                    scaleValues(planet.planetDensity))
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        var dataset = ScatterDataSet(planetEntryList, label)
        return dataset
    }

    fun eccentricityPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryOrbitalEccentricity != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    planet.planetaryOrbitalEccentricity.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        val dataSet = ScatterDataSet(planetEntryList, label)
        return dataSet
    }

    fun densityMassDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetDensity != null && planet.planetaryMassJupiter != null) {
                planetEntryList.add(Entry(
                    scaleValues(planet.planetaryMassJupiter),
                    scaleValues(planet.planetDensity)))
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        val dataset = ScatterDataSet(planetEntryList, label)
        return dataset
    }

    fun earthMassRadiusDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryMassEarth != null && planet.planetaryRadiusEarth != null) {
                planetEntryList.add(Entry(
                    scaleValues(planet.planetaryMassEarth),
                    scaleValues(planet.planetaryRadiusEarth)))
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        val dataset = ScatterDataSet(planetEntryList, label)
        return dataset
    }


    //Bar Charts
    fun createDiscoveryYearDataSet(listOfPlanets: List<Planet>, label: String): BarDataSet {
        val planetEntryList = mutableListOf<BarEntry>()
        val yearDiscoveryNumberMap = listOfPlanets.groupBy { planet -> planet.discoveryYear }
        yearDiscoveryNumberMap.keys.forEach { year ->
            val yearBarEntry: BarEntry
            if(year != null){
                val numberOfPlanets = yearDiscoveryNumberMap[year]?.size.let { it?.toFloat() }
                yearBarEntry = BarEntry(year.toFloat(), numberOfPlanets!! )
                planetEntryList.add(yearBarEntry)
            }
        }
        Timber.d("${planetEntryList.size}")
        return BarDataSet(planetEntryList, label)
    }


    //Custom Scatter Plot
    fun createCustomDistribution(listOfPlanets: List<Planet>, label: String, xValue: String, yValue: String): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        Timber.d("x value: $xValue")
        Timber.d("x value: $yValue")
        listOfPlanets.forEach { planet ->
            //How to add x and y value to planet.xValue and planet.yValue
        }
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

}



