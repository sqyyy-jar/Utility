/**
 * @author sqyyy
 */

package dev.sqyyy.utility.eventadapter

import java.util.function.Consumer

/**
 * A simple, modern **EventAdapter** using generics.
 *
 * @param E the default event type, all events should implement
 */
class GenericEventAdapter<E : Any> {
    private val map = hashMapOf<Class<*>, ArrayList<Consumer<*>>>()

    /**
     * **Register** a listener to the adapter.
     *
     * @param L the type of the listener to register
     * @param functionClass the class of the listener to register
     * @param function the function to register
     */
    fun <L : E> registerListener(functionClass: Class<L>, function: Consumer<L>) {
        if (!map.containsKey(functionClass))
            map[functionClass] = arrayListOf(function)
        else
            map[functionClass]?.add(function)
    }

    /**
     * **Call** an event with the adapter.
     *
     * @param T the type of the event to call
     * @param functionClass the class of the event to register
     * @param arg the argument to parse into the functions
     */
    fun <T : E> callEvent(functionClass: Class<T>, arg: T) {
        map[functionClass]?.forEach { (it as Consumer<T>).accept(arg) }
    }
}