package sabana.mrp1.services

import org.springframework.stereotype.Component
import sabana.mrp1.entities.*
import sabana.mrp1.repositories.IngredienteRepository
import sabana.mrp1.repositories.OrdenCompraMesRepository
import sabana.mrp1.repositories.ProductoRepository
import sabana.mrp1.repositories.RegistroVentasRepository

@Component
class InventoryPlanningService(
        private val registroVentasRepository: RegistroVentasRepository,
        private val ordenCompraMesRepository: OrdenCompraMesRepository,
        private val ingredienteRepository: IngredienteRepository,
        private val productoRepository: ProductoRepository
) {

    inner class MonthlyIngredientWrapper(val mes: Int, val ingrediente: Ingrediente, val cantidad: Int) {
        override fun toString(): String {
            return "(Mo: $mes - Ing: $ingrediente - Q: $cantidad)"
        }
    }

    init {
        // Desired output is: Mes (index from 0-11), Ingrediente, Cantidad represented as Map<Int, Pair<Ingrediente, Int>>.

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

        // Step 5
        val aggregatedWrappers =
                wrapper

//                        .groupBy { it.mes }



        println(aggregatedWrappers)

    }

    fun planUsingCurrentPrediction(): Unit {

    }
}