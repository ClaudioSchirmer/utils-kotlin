package dev.cschirmer.utils.kotlin.json

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Convert {
	inline fun <reified TObject : Any> getObjectFromJson(json: String): TObject? {
		return try {
			jacksonObjectMapper().readValue(json, TObject::class.java)
		} catch (e: Exception) {
			null
		}
	}
	fun getJsonFromObject(obj: Any): String = jacksonObjectMapper().writeValueAsString(obj)
}