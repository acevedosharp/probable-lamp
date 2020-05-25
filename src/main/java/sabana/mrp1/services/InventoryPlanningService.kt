package sabana.mrp1.services

import org.springframework.stereotype.Component
import sabana.mrp1.entities.ComportamientoMes
import sabana.mrp1.entities.Ingrediente
import sabana.mrp1.entities.IngredienteProducto
import sabana.mrp1.entities.RegistroVentas
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
    // Mes (0-11), Ingrediente, Cantidad
    private val accumulations: List<Pair<Ingrediente, Int>> = ingredienteRepository.findAll().map { it to 0 }

    init {
        val productoToVentas = registroVentasRepository.findAllByTiempoEquals("futuro").map {
            it.producto to it.comportamientosMes
        }

        productoToVentas.forEach {
            println(it)
        }

        productoToVentas.forEach { pair ->
            pair.second.forEach { comportamiento ->
                pair.first.ingredientesProducto.forEach {

                    println("Mes ${comportamiento.mes}: ${pair.first.nombre} - ${pair.first.ingredientesProducto.map { it.cantidad*comportamiento.ventas to it.metricaIngrediente}}")
                }
            }
        }

//        productoToVentas.forEach { println(it) }
    }

    fun planUsingCurrentPrediction(): Unit {

        registroVentasRepository.findAll().forEach { registroVentas: RegistroVentas ->
            registroVentas.comportamientosMes.forEach { comportamientoMes: ComportamientoMes ->
                registroVentas.producto.ingredientesProducto.forEach { ingredienteProducto: IngredienteProducto ->

                }
            }
        }
    }
}