package es.muvment.banco_jualbe.dao

interface PojoDAO {
    fun add(obj: Any?): Long
    fun update(obj: Any?): Int?
    fun delete(obj: Any?)
    fun search(obj: Any?): Any?
    fun getAll(): ArrayList<*>?
}