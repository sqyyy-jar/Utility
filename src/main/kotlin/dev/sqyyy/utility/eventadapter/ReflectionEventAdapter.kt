/**
 * @author sqyyy
 */

package dev.sqyyy.utility.eventadapter

import java.lang.reflect.Method
import kotlin.collections.ArrayList

/**
 * A simple **EventAdapter** using reflection.
 *
 * @param E the default event type, all events should implement
 * @param L the default listener type, all listeners should implement
 * @param eventClass the class of E
 */
class ReflectionEventAdapter<E : Any, L : Any>(private val eventClass: Class<E>) {
    private val map = hashMapOf<Class<*>, ArrayList<EventMethod<L>>>()

    /**
     * **Register** a listener to the adapter.
     *
     * @param arg the listener instance to register
     */
    fun registerListener(arg: L) {
        arg.javaClass.declaredMethods
            .filter { it.isAnnotationPresent(EventHandler::class.java) }
            .filter { it.parameterTypes.size == 1 }
            .filter { map.containsKey(it.parameterTypes[0]) }
            .forEach { map[it.parameterTypes[0]]?.add(EventMethod(arg, it)) }
    }

    /**
     * **Register** an eventtype to the adapter so it can get called.
     *
     * @param event the class of the event to register
     */
    fun registerEvent(event: Class<out E>) {
        if (event == eventClass) return
        if (!map.containsKey(event))
            map[event] = arrayListOf()
    }

    /**
     * **Call** an event with the adapter. The eventtype need to be registered
     *
     * @param arg the instance of the event to call
     */
    fun callEvent(arg: E) {
        map[arg.javaClass]?.forEach { it.method.invoke(it.parent, arg) }
    }

    @Target(AnnotationTarget.FUNCTION)
    annotation class EventHandler

    data class EventMethod<T>(val parent: T, val method: Method)
}