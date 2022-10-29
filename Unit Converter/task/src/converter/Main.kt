package converter

import java.util.*

val scanner = Scanner(System.`in`)

/**
 * Types of measure units
 */
enum class UnitType { DISTANCE, WEIGHT, TEMPERATURE }

/**
 * Holds unit types and required information about them to make conversation operations
 * @param type unit type
 * @param singular singular definition of the unit
 * @param plural plural definition of the unit
 * @param scale scale according to the standard unit (for distance and weight)
 * @param definitions possible definitions by users
 */
enum class Unit(
    val type: UnitType,
    val singular: String,
    val plural: String,
    val scale: Double = 0.0,
    vararg val definitions: String
) {
    M(UnitType.DISTANCE, "meter", "meters", 1.0, "m", "meter", "meters"),
    CM(UnitType.DISTANCE, "centimeter", "centimeters", 0.01, "cm", "centimeter", "centimeters"),
    MM(UnitType.DISTANCE, "millimeter", "millimeters", 0.001, "mm", "millimeter", "millimeters"),
    KM(UnitType.DISTANCE, "kilometer", "kilometers", 1000.0, "km", "kilometer", "kilometers"),
    IN(UnitType.DISTANCE, "inch", "inches", 0.0254, "in", "inch", "inches"),
    MI(UnitType.DISTANCE, "mile", "miles", 1609.35, "mi", "mile", "miles"),
    YD(UnitType.DISTANCE, "yard", "yards", 0.9144, "yd", "yard", "yards"),
    FT(UnitType.DISTANCE, "foot", "feet", 0.3048, "ft", "foot", "feet"),
    G(UnitType.WEIGHT, "gram", "grams", 1.0, "g", "gram", "grams"),
    KG(UnitType.WEIGHT, "kilogram", "kilograms", 1000.0, "kg", "kilogram", "kilograms"),
    MG(UnitType.WEIGHT, "milligram", "milligrams", 0.001, "mg", "milligram", "milligrams"),
    LB(UnitType.WEIGHT, "pound", "pounds", 453.592, "lb", "pound", "pounds"),
    OZ(UnitType.WEIGHT, "ounce", "ounces", 28.3495, "oz", "ounce", "ounces"),
    CS(
        UnitType.TEMPERATURE,
        "degree Celsius",
        "degrees Celsius",
        0.0,
        "degreecelsius",
        "celsius",
        "degreescelsius",
        "dc",
        "c"
    ),
    FH(
        UnitType.TEMPERATURE,
        "degree Fahrenheit",
        "degrees Fahrenheit",
        0.0,
        "degreefahrenheit",
        "fahrenheit",
        "degreesfahrenheit",
        "df",
        "f"
    ),
    KV(UnitType.TEMPERATURE, "kelvin", "kelvins", 0.0, "kelvins", "kelvin", "k"),
}

/**
 * Gets input from user, parses it and if possible converts units from one to another
 */
class UnitConverter {
    fun start() {
        while (true) {
            println("Enter what you want to convert (or exit):")
            val input = scanner.nextLine().replace("degrees ", "degrees", true)
                .replace("degree ", "degree", true)
            if (input == "exit") break
            else {
                try {
                    val number: Double = input.split(" ")[0].toDouble()
                    val from: String = input.split(" ")[1]
                    val to: String = input.split(" ")[3]
                    convertUnits(number, from, to)
                } catch (e: Exception) {
                    println("Parse error")
                }
            }
        }
    }

    /**
     * Converts distance and weight definitions, if can't, gives error message
     * @param number unit to convert
     * @param from unit type to convert from
     * @param to unit type to convert to
     */
    private fun convertUnits(number: Double, from: String, to: String) {
        val message: String = if (unitType(from) != unitType(to) || !isUnit(from) || !isUnit(to)) {
            val fromUnit: String = if (isUnit(from)) getUnit(from)!!.plural else "???"
            val toUnit: String = if (isUnit(to)) getUnit(to)!!.plural else "???"
            "Conversion from $fromUnit to $toUnit is impossible"
        } else when {
            getUnit(from) == getUnit(to) -> "$number ${if (number == 1.0) getUnit(from)!!.singular else getUnit(from)!!.plural} is $number ${
                if (number == 1.0) getUnit(
                    to
                )!!.singular else getUnit(to)!!.plural
            }"

            unitType(from) == UnitType.TEMPERATURE -> {
                val converted: Double = when (getUnit(from)!!.name) {
                    "CS" -> if (getUnit(to)!!.name == "KV") number + 273.15 else number * 9.0 / 5.0 + 32.0

                    "FH" -> 5.0 / 9.0 * (if (getUnit(to)!!.name == "CS") number - 32.0 else number + 459.67)

                    else -> if (getUnit(to)!!.name == "CS") number - 273.15 else number * 9.0 / 5.0 - 459.67
                }
                "$number ${if (number == 1.0) getUnit(from)!!.singular else getUnit(from)!!.plural} is $converted ${
                    if (converted == 1.0) getUnit(
                        to
                    )!!.singular else getUnit(to)!!.plural
                }"
            }

            else -> {
                if (number < 0) "${if (unitType(from) == UnitType.DISTANCE) "Length" else "Weight"} shouldn't be negative."
                else {
                    val converted = number * getUnit(from)!!.scale / getUnit(to)!!.scale
                    "$number ${if (number == 1.0) getUnit(from)!!.singular else getUnit(from)!!.plural} is $converted ${
                        if (converted == 1.0) getUnit(
                            to
                        )!!.singular else getUnit(to)!!.plural
                    }"
                }
            }
        }

        println(message)
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
fun isUnit(unit: String): Boolean {
    for (enum in Unit.values()) if (unit.lowercase() in enum.definitions) return true
    return false
}

/**
 * Checks the given string and if it's definition of a unit returns type of that unit
 * @param unit given string
 * @return unit type if matches any, null otherwise
 */
fun unitType(unit: String): UnitType? {
    for (enum in Unit.values()) if (unit.lowercase() in enum.definitions) return enum.type
    return null
}

/**
 * Checks the given string and if it's definition of a unit returns that unit
 * @param unit given string
 * @return unit if matches any, null otherwise
 */
fun getUnit(unit: String): Unit? {
    for (enum in Unit.values()) if (unit.lowercase() in enum.definitions) return enum
    return null
}
