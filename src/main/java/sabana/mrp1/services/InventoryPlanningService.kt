package sabana.mrp1.services

import org.springframework.stereotype.Service
import sabana.mrp1.entities.*
import sabana.mrp1.repositories.OrdenCompraMesRepository
import sabana.mrp1.repositories.RegistroVentasRepository

@Service
class InventoryPlanningService(
        private val registroVentasRepository: RegistroVentasRepository,
        private val ordenCompraMesRepository: OrdenCompraMesRepository
) {

    inner class MonthlyIngredientWrapper(val mes: Int, val ingrediente: Ingrediente, val cantidad: Int) {
        override fun toString(): String {
            return "(Mo: $mes - Ing: $ingrediente - Q: $cantidad)"
        }
    }


    fun planUsingCurrentFuturePrediction(): Unit {
        // Desired result is: Mes (index from 0-11), Ingrediente, Cantidad represented as Map<Int, Pair<Ingrediente, Int>>.

        var previousTimestamp:Long = System.currentTimeMillis()
        var tempCurrent:Long;

        println("Began transformations at: ${previousTimestamp}")

        // Step 1: We extract Producto and Comportamientos from the RegistroVentasRepository. Size is the number of Productos.
        val compToProd: List<Pair<List<ComportamientoMes>, Producto>> =
                registroVentasRepository
                        .findAllByTiempoEquals("futuro")
                        .map { it.comportamientosMes to it.producto }

        // Step 2: We flatten our collection to List<Pair<ComportamientoMes, Producto>>
        val compToProdFlat: List<Pair<ComportamientoMes, Producto>> =
                compToProd.flatMap { pair: Pair<List<ComportamientoMes>, Producto> ->
                    pair.first.map {
                        it to pair.second
                    }
                }

        // Step 3: We map Comportamiento to the List<IngredienteProducto> that we extract from producto
        val compToIngredientes: List<Pair<ComportamientoMes, List<IngredienteProducto>>> =
                compToProdFlat.map { it.first to it.second.ingredientesProducto.toList() }


        // Step 4: Reshape collection to List<MonthlyIngredientWrapper>, sorry, I don't understand this either!
        val wrapper =
                compToIngredientes
                        .groupBy { it.first.mes } // Map(month, Pair(Comp, List(Ing)))
                        .flatMap { entry: Map.Entry<Int, List<Pair<ComportamientoMes, List<IngredienteProducto>>>> ->
                            entry.value.map { pair: Pair<ComportamientoMes, List<IngredienteProducto>> ->
                                pair.second.map {
                                    pair.first to it
                                } // List<Pair<ComportamientoMes, IngredienteProducto>>
                                        .map {
                                            MonthlyIngredientWrapper(
                                                    entry.key,
                                                    it.second.ingrediente,
                                                    (it.first.ventas * it.second.cantidad).toInt()
                                            )
                                        }
                            }
                        }.flatten()

        // Step 5: finally List<MonthlyIngredientWrapper>
        val accumulatedWrappers =
                wrapper
                        .groupBy { it.mes }
                        .values
                        .map { it.groupBy { it.ingrediente } } // List<Map<Ingrediente, List<MonthlyIngredientWrapper>>>
                        .mapIndexed { index, item ->
                            item.map {
                                MonthlyIngredientWrapper(
                                        index + 1,
                                        it.key,
                                        it.value.sumBy { it.cantidad }
                                )
                            }
                        }.flatten()

        tempCurrent = System.currentTimeMillis()
        println("Ended transformations at: ${tempCurrent} - delta: ${tempCurrent - previousTimestamp}")


        previousTimestamp = System.currentTimeMillis()
        println("Began writing at: ${previousTimestamp}")

        // Step 6: Persist to db
        ordenCompraMesRepository.saveAll(accumulatedWrappers.map {
            OrdenCompraMes(
                    null,
                    it.ingrediente,
                    it.cantidad,
                    it.mes
            )
        })

        tempCurrent = System.currentTimeMillis()
        println("Ended writing at: ${tempCurrent} - delta: ${tempCurrent - previousTimestamp}")
    }

    fun groupedByMonth(): Map<Int, List<OrdenCompraMes>> = ordenCompraMesRepository.findAll().groupBy { it.mes }

    fun existOrdenes(): Boolean = ordenCompraMesRepository.count() != 0L

    fun clearOrdenes(): Unit = ordenCompraMesRepository.deleteAll()

}