package com.rpfcoding.noteappkmm.data.util

import com.rpfcoding.noteappkmm.domain.model.Barangay
import com.rpfcoding.noteappkmm.domain.model.Municipality
import com.rpfcoding.noteappkmm.domain.model.Province
import com.rpfcoding.noteappkmm.domain.model.Region
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject


fun getRegions(): List<Region> {
    val regionList = mutableListOf<Region>()

    val regions = Json.parseToJsonElement(Constants.addressJson).jsonObject

    val regionArray = regions.entries.toList()

    for (i in regionArray.indices) {
        val regionObj = regionArray[i].value.jsonObject
        val provinces = regionObj["province_list"]?.jsonObject?.entries?.toList()

        val listProvince = mutableListOf<Province>()

        for (j in provinces?.indices!!) {
            val provinceObj = provinces[j].value.jsonObject

            val municipalities = provinceObj["municipality_list"]?.jsonObject?.entries?.toList()

            val listMunicipality = mutableListOf<Municipality>()

            for (k in municipalities?.indices!!) {
                val municipalityObj = municipalities[k].value.jsonObject
                val barangayNames = municipalityObj["barangay_list"]?.jsonArray

                val listBarangay = mutableListOf<Barangay>()

                for (l in barangayNames?.indices!!) {
                    listBarangay.add(
                        Barangay(
                            name = barangayNames[l].toString().replace("\"", "").trim(),
                        )
                    )
                }

                listMunicipality.add(
                    Municipality(
                        name = municipalities[k].key,
                        barangayList = listBarangay
                    )
                )
            }

            listProvince.add(
                Province(
                    name = provinces[j].key,
                    municipalities = listMunicipality
                )
            )
        }

        regionList.add(
            Region(
                name = regionObj["region_name"].toString().replace("\"", "").trim(),
                provinces = listProvince
            )
        )
    }

    return regionList
}

const val dictionaryOfBase64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

fun ByteArray.encodeBase64(): ByteArray {
    val output = mutableListOf<Byte>()
    var padding = 0
    var position = 0
    while (position < this.size) {
        var b = this[position].toInt() and 0xFF shl 16 and 0xFFFFFF
        if (position + 1 < this.size) b = b or (this[position + 1].toInt() and 0xFF shl 8) else padding++
        if (position + 2 < this.size) b = b or (this[position + 2].toInt() and 0xFF) else padding++
        for (i in 0 until 4 - padding) {
            val c = b and 0xFC0000 shr 18
            output.add(dictionaryOfBase64[c].toByte())
            b = b shl 6
        }
        position += 3
    }
    for (i in 0 until padding) {
        output.add('='.toByte())
    }
    return output.toByteArray()
}

class MyHelper {

    @Throws(Exception::class)
    fun decodeBase64(bytes: ByteArray): String {
        return try {
            val table = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
                -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
                -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)

            val output = mutableListOf<Int>()
            var position = 0
            while (position < bytes.size) {
                var b: Int
                if (table[bytes[position].toInt()] != -1) {
                    b = table[bytes[position].toInt()] and 0xFF shl 18
                } else {
                    position++
                    continue
                }
                var count = 0
                if (position + 1 < bytes.size && table[bytes[position + 1].toInt()] != -1) {
                    b = b or (table[bytes[position + 1].toInt()] and 0xFF shl 12)
                    count++
                }
                if (position + 2 < bytes.size && table[bytes[position + 2].toInt()] != -1) {
                    b = b or (table[bytes[position + 2].toInt()] and 0xFF shl 6)
                    count++
                }
                if (position + 3 < bytes.size && table[bytes[position + 3].toInt()] != -1) {
                    b = b or (table[bytes[position + 3].toInt()] and 0xFF)
                    count++
                }
                while (count > 0) {
                    val c = b and 0xFF0000 shr 16
                    output.add(c.toChar().toInt())
                    b = b shl 8
                    count--
                }
                position += 4
            }
            String(output.map { it.toByte() }.toByteArray())
        } catch (e: Exception) {
            ""
        }
    }
}

