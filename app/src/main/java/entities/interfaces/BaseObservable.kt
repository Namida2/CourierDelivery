package entities.interfaces

interface BaseObservable<TSub> {
    fun subscribe(subscriber: TSub)
    fun unsubscribe(subscriber: TSub)
}