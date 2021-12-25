package com.example.courierdelivery.models

import com.example.courierdelivery.models.interfaces.RouteMapsModelInterface
import com.example.courierdelivery.models.services.RouteMapsService
import com.example.courierdelivery.models.services.RouteMapsSubscriber
import entities.ErrorMessage
import entities.RouteMapInfo
import entities.routeMaps.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RouteMapsFragmentModel @Inject constructor(
    private val routeMapsService: RouteMapsService,
) : RouteMapsModelInterface {

    override fun readRouteMaps(
        onSuccess: (routeMaps: List<RouteMapInfo>) -> Unit,
        onError: (message: ErrorMessage?) -> Unit,
    ) {
        CoroutineScope(IO).launch {
            getAllRouteMaps()
            withContext(Main) {
                onSuccess(routeMapsService.routeMapsInfo.toList())
            }
        }
    }

    override fun getRouteMapInfoById(routeMapId: Int): RouteMapInfo =
        routeMapsService.getRouteMapInfoById(routeMapId)

    override fun getRouteMapsInfo(): List<RouteMapInfo> =
        routeMapsService.routeMapsInfo.toList()

    override fun setCurrentRouteMapInfo(routeMapId: Int) {
        routeMapsService.currentRouteMapInfo =
            routeMapsService.getRouteMapInfoById(routeMapId).also {
                it.isSelected = true
            }
    }

    override fun getCurrentRouteMapId(): Int? =
        routeMapsService.currentRouteMapInfo?.routeMap?.id

    override fun subscribeOnRouteMapsChanges(subscriber: RouteMapsSubscriber) {
        routeMapsService.subscribeOnRouteMapsChanges(subscriber)
    }
    override fun unsubscribeOnRouteMapsChanges(subscriber: RouteMapsSubscriber) {
        routeMapsService.unsubscribeOnRouteMapsChanges(subscriber)
    }

    private suspend fun getAllRouteMaps() {
        delay(1000)
        val routeMaps = listOf(
            RouteMapInfo(
                RouteMap(
                    0,
                    "+7(000)000-00-00",
                    0,
                    mutableListOf(
                        RouteItem(
                            AddressTypes.PROVIDER,
                            0,
                        ),
                        RouteItem(
                            AddressTypes.PROVIDER,
                            1,
                        ),
                        RouteItem(
                            AddressTypes.CLIENT,
                            0,
                        ),
                        RouteItem(
                            AddressTypes.CLIENT,
                            1,
                        ),
                        RouteItem(
                            AddressTypes.PROVIDER,
                            2
                        ),
                        RouteItem(
                            AddressTypes.PROVIDER,
                            3
                        ),
                        RouteItem(
                            AddressTypes.CLIENT,
                            2
                        ),
                        RouteItem(
                            AddressTypes.CLIENT,
                            3
                        ),
                    ),
                    "0%",
                    55.35030001730317,
                    86.09264662744278
                ),
                listOf(
                    Client(
                        0,
                        0,
                        "Васильвева ул., 20 ,",
                        55.350346975591776,
                        86.0926499899407,
                        "+7(000)000-00-00",
                        "",
                        "New"
                    ),

                    Client(
                        1,
                        1,
                        "Красноармейская улица, 136",
                        55.35841226217312,
                        86.071664605835,
                        "+7(111)111-00-00",
                        "",
                        "New"
                    ),
                    Client(
                        2,
                        2,
                        "Красная улица, 15",
                        55.35109313264278,
                        86.06750181751973,
                        "+7(222)222-00-00",
                        "",
                        "New"
                    ),
                    Client(
                        3,
                        3,
                        "Весенняя улица, 22 ",
                        55.35109313264278,
                        86.06750181751973,
                        "+7(333)333-00-00",
                        "",
                        "New"
                    )
                ),
                listOf(
                    Provider(
                        0,
                        "Провинция на Советском",
                        "Весенняя улица, 28 ",
                        55.35786404227941,
                        86.07855797588805,
                        "+7(111)111-11-11",
                        ""
                    ),
                    Provider(
                        1,
                        "Леруа Мерлен",
                        "улица 50 лет Октября, 21А ",
                        55.35748524726899,
                        86.06290987576993,
                        "+7(222)222-11-11",
                        "Commentary"
                    ),
                    Provider(
                        2,
                        "Бизон, автоцентр",
                        "улица Дзержинского, 20 ",
                        55.35748524726899,
                        86.06290987576993,
                        "+7(222)222-11-11",
                        "Commentary0"
                    ),
                    Provider(
                        3,
                        "БСезонАвто",
                        "проспект Ленина, 20 ",
                        55.35337562924404,
                        86.07497969434073,
                        "+7(333)333-11-11",
                        "Commentary0"
                    )
                )
            ),
            RouteMapInfo(
                RouteMap(
                    1,
                    "+7(111)111-11-11",
                    1,
                    mutableListOf(
                        RouteItem(
                            AddressTypes.PROVIDER,
                            10
                        ),
                        RouteItem(
                            AddressTypes.CLIENT,
                            10
                        ),
                    ),
                    "0%",
                    55.35030001730317,
                    86.09264662744278
                ),
                listOf(
                    Client(
                        10,
                        99,
                        "проспект Ленина, 21А ",
                        55.35109313264278,
                        86.06750181751973,
                        "+7(000)000-00-00",
                        "",
                        "New"
                    ),
                ),
                listOf(
                    Provider(
                        99,
                        "Провинция на Советском",
                        "улица Сарыгина, 12А",
                        55.33960185819431,
                        86.10813385176446,
                        "+7(111)111-11-11",
                        ""
                    ),
                )
            )
        )

        routeMapsService.routeMapsInfo.addAll(routeMaps)
    }
}




