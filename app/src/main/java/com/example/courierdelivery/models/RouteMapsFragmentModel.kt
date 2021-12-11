package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteMapServicesInterface
import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import com.example.courierdelivery.models.services.RouteMapsServices
import entities.ErrorMessage
import entities.routeMaps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RouteMapsFragmentModel @Inject constructor(
    private val routeMapsServices: RouteMapServicesInterface,
) : RouteMapsModelInterface {

    override fun getRouteMaps(
        onSuccess: (isEmpty: Boolean) -> Unit,
        onError: (message: ErrorMessage?) -> Unit,
    ) {
        CoroutineScope(IO).launch {
            getAllClients()
            getAllProviders()
            getAllRouteMaps()
            withContext(Main) {
                onSuccess(false)
            }
        }
    }

    private suspend fun getAllClients() {
        delay(1000)
        val clients = listOf(

            Client(
                0,
                0,
                55.350346975591776,
                86.0926499899407,
                "+7(000)000-00-00",
                "",
                "New"
            ),

            Client(
                1,
                1,
                55.35841226217312,
                86.071664605835,
                "+7(111)111-00-00",
                "",
                "New"
            ),
            Client(
                2,
                2,
                55.35109313264278,
                86.06750181751973,
                "+7(222)222-00-00",
                "",
                "New"
            ),
            Client(
                3,
                3,
                55.35109313264278,
                86.06750181751973,
                "+7(333)333-00-00",
                "",
                "New"
            )


        )
        routeMapsServices.clientsService.clients.addAll(clients)
    }

    private suspend fun getAllProviders() {
        delay(1000)
        val providers = listOf(

            Provider(
                0,
                "Провинция на Советском",
                55.35786404227941,
                86.07855797588805,
                "+7(111)111-11-11",
                ""
            ),
            Provider(
                1,
                "Леруа Мерлен",
                55.35748524726899,
                86.06290987576993,
                "+7(222)222-11-11",
                "Commentary"
            ),
            Provider(
                2,
                "Бизон, автоцентр",
                55.35748524726899,
                86.06290987576993,
                "+7(222)222-11-11",
                "Commentary0"
            ),
            Provider(
                3,
                "БСезонАвто",
                55.35337562924404,
                86.07497969434073,
                "+7(333)333-11-11",
                "Commentary0"
            ),

            )
        routeMapsServices.providerService.providers.addAll(providers)
    }

    private suspend fun getAllRouteMaps() {
        delay(1000)
        val routeMaps = listOf(
            RouteMap(
                "+7(904)377-57-75",
                0,
                listOf(
                    RouteItem(
                        AddressType.PROVIDER,
                        0
                    ),
                    RouteItem(
                        AddressType.PROVIDER,
                        1
                    ),
                    RouteItem(
                        AddressType.CLIENT,
                        0
                    ),
                    RouteItem(
                        AddressType.CLIENT,
                        1
                    ),
                    RouteItem(
                        AddressType.PROVIDER,
                        2
                    ),
                    RouteItem(
                        AddressType.PROVIDER,
                        3
                    ),
                    RouteItem(
                        AddressType.CLIENT,
                        2
                    ),
                    RouteItem(
                        AddressType.CLIENT,
                        3
                    ),
                ),
                "0%",
                55.35030001730317,
                86.09264662744278
            ),
            RouteMap(
                "+7(904)377-57-75",
                1,
                listOf(

                ),
                "0%",
                55.35030001730317,
                86.09264662744278
            )
        )
        routeMapsServices.routeMapsService.routeMaps.addAll(routeMaps)
    }
}




