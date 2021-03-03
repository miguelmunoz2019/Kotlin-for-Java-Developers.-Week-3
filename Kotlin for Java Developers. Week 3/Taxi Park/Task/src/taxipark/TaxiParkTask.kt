package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.minus( trips.associateBy{ it.driver }.keys)
/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        if(minTrips==0) allPassengers else trips.associate{ it to it.passengers }.values.toList().flatten().groupBy {it.name}.filter { it.value.size >=minTrips }.values.map { it[0] }.toSet()
/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        trips.filter { it.driver==driver }.associate { it to it.passengers }.values.toList().flatten().groupBy {it.name}.filter { it.value.size >1}.values.map { it[0] }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        trips.filter { it.discount != null }.filter { it.discount != 0.0 }.associate{ it to it.passengers }.values.toList().flatten().groupBy {it.name}.values.toList().associate { it[0] to it.size }.filter { (nombre, descuentos)-> trips.associate{ it to it.passengers }.values.toList().flatten().groupBy {it.name}.values.toList().associate { it[0].name to it.size }.getValue(nombre.name)-descuentos<descuentos}.keys

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty())
        return null
    else
    {
        var lista: List<IntRange> = mutableListOf()
        for (viaje in trips)
        {
            var duracion=viaje.duration
            while (duracion%10!=0)
                duracion-=1

            var rango = IntRange(duracion,duracion+9)
            lista += listOf<IntRange>(rango)
        }
        var contador=0
        var rango:IntRange = 0..0
        for (elemento in lista)
        {
            var cuenta=0
            for (elemento2 in lista)
            {
                if (elemento==elemento2)
                    cuenta+=1
            }
            if (cuenta>=contador)
            {
                rango=elemento
                contador=cuenta
            }
    }
    return rango

}
    }

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (trips.isEmpty())
        return false
    else
    {
    var total= trips.sumByDouble { it.cost }
    var obtenidos= trips.map { it.driver to it.cost }.groupBy { it.first }
    var obtenidos2=obtenidos.map { a : Map.Entry<Driver, List<Pair<Driver, Double>>> -> a.key to a.value.sumByDouble {it.second } }
    obtenidos2=obtenidos2.sortedByDescending { it.second }
    var suma=0.0
    var contador=0
    var comparador=(total*0.80)
    while(suma<=comparador && contador<obtenidos.size)
    {
        suma+=(obtenidos2[contador].second)
        contador+=1

        if(suma==comparador)
            break
    }
    return contador<=allDrivers.size*0.20
        }
}