package converter

import java.util.*

val scanner = Scanner(System.`in`)

enum class Distance(val scale: Double, vararg val units: String) {
    M(1.0, "m", "meter", "meters"),
    CM(0.01, "cm", "centimeter", "centimeters"),
    MM(0.001, "mm", "millimeter", "millimeters"),
    KM(1000.0, "km", "kilometer", "kilometers"),
    IN(0.0254, "in", "inch", "inches"),
    MI(1609.35, "mi", "mile", "miles"),
    YD(0.9144, "yd", "yard", "yards"),
    FT(0.3048, "ft", "foot", "feet"),
}

enum class Weight(val scale: Double, vararg val units: String) {
    G(1.0, "g", "gram", "grams"),
    KG(1000.0, "kg", "kilogram", "kilograms"),
    MG(0.001, "mg", "milligram", "milligrams"),
    LB(453.592, "lb", "pound", "pounds"),
    OZ(28.3495, "oz", "ounce", "ounces"),
}

class UnitConverter {
    fun start() {
        while (true) {
            println("Enter what you want to convert (or exit):")
            val input = scanner.nextLine()
            if (input == "exit") break
            else {
                val number: Double = input.split(" ")[0].toDouble()
                val from: String = input.split(" ")[1]
                val to: String = input.split(" ")[3]
                convertUnits(number, from, to)
            }
        }
    }

    /**
     * Converts distance and weight units, if can't, gives error message
     * @param number unit to convert
     * @param from unit type to convert from
     * @param to unit type to convert to
     */
    private fun convertUnits(number: Double, from: String, to: String) {
        when {
            isDistance(from) && isDistance(to) -> {
                val fromUnit = distanceUnitType(from)!!
                val toUnit = distanceUnitType(to)!!
                val fromScale = fromUnit.scale
                val toScale = toUnit.scale
                val converted = number * fromScale / toScale
                val message = "$number ${fromUnit.units[if (number == 1.0) 1 else 2]} is $converted " +
                        toUnit.units[if (converted == 1.0) 1 else 2]
                println(message)
            }

            isWeight(from) && isWeight(to) -> {
                val fromUnit = weightUnitType(from)!!
                val toUnit = weightUnitType(to)!!
                val fromScale = fromUnit.scale
                val toScale = toUnit.scale
                val converted = number * fromScale / toScale
                val message = "$number ${fromUnit.units[if (number == 1.0) 1 else 2]} is $converted " +
                        toUnit.units[if (converted == 1.0) 1 else 2]
                println(message)
            }

            else -> {
                val fromUnit: String = if (isUnit(from)) {
                    if (isWeight(from)) weightUnitType(from)!!.units[2]
                    else distanceUnitType(from)!!.units[2]
                } else "???"
                val toUnit: String = if (isUnit(to)) {
                    if (isWeight(to)) weightUnitType(to)!!.units[2]
                    else distanceUnitType(to)!!.units[2]
                } else "???"
                val message = "Conversion from $fromUnit to $toUnit is impossible"
                println(message)
            }
        }
    }
}

fun main() {
    val converter = UnitConverter()
    converter.start()
}

/**
 * Checks for the given string if it defines a unit or not
 * @param unit string to check
 * @return true if [unit] defines a unit, false otherwise
 */
fun isUnit(unit: String): Boolean = (isDistance(unit) || isWeight(unit))

/**
 * Checks for the given string if it defines a distance unit or not
 * @param unit string to check
 * @return true if [unit] defines a distance unit, false otherwise
 */
fun isDistance(unit: String): Boolean {
    for (enum in Distance.values()) if (unit.lowercase() in enum.units) return true
    return false
}

/**
 * Checks for the given string if it defines a weight unit or not
 * @param unit string to check
 * @return true if [unit] defines a weight unit, false otherwise
 */
fun isWeight(unit: String): Boolean {
    for (enum in Weight.values()) if (unit.lowercase() in enum.units) return true
    return false
}

/**
 * Defines type of distance unit according to the given string
 * @param unit given string
 * @return unit type if exists, null else
 */
fun distanceUnitType(unit: String): Distance? {
    for (enum in Distance.values()) if (unit.lowercase() in enum.units) return enum
    return null
}

/**
 * Defines type of weight unit according to the given string
 * @param unit given string
 * @return unit type if exists, null else
 */
fun weightUnitType(unit: String): Weight? {
    for (enum in Weight.values()) if (unit.lowercase() in enum.units) return enum
    return null
}
