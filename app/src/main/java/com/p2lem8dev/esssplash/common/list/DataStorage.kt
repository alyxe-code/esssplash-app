package com.p2lem8dev.esssplash.common.list

interface DataStorage<T> {
    val size: Int
    fun save(items: List<T>)
    fun clear()
    fun get(startPosition: Int, size: Int): List<T>
    fun all(): List<T>
}