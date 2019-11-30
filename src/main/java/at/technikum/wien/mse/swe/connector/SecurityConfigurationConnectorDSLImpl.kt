package at.technikum.wien.mse.swe.connector

import at.technikum.wien.mse.swe.SecurityConfigurationConnector
import at.technikum.wien.mse.swe.model.SecurityConfiguration
import java.lang.UnsupportedOperationException
import java.nio.file.Path

class SecurityConfigurationConnectorDSLImpl : SecurityConfigurationConnector {
    override fun read(file: Path?): SecurityConfiguration =
            fixedWidthBlock(file ?: throw UnsupportedOperationException("No file path provided.")) {
                "isin" composedOf {
                    "value" block {
                        index = 40
                        length = 12
                    }
                }
                "riskCategory" composedOf {
                    "code" block {
                        index = 52
                        length = 2
                    }
                }
                "name" block {
                    index = 54
                    length = 30
                    paddingChar = ' '
                    alignment = FieldAlignment.RIGHT
                }
                "yearHighest" composedOf {
                    "currency" block {
                        index = 84
                        length = 3
                        paddingChar = ' '
                        alignment = FieldAlignment.LEFT
                    }
                    "value" block {
                        index = 87
                        length = 10
                        paddingChar = ' '
                        alignment = FieldAlignment.RIGHT
                    }
                }
                "yearLowest" composedOf {
                    "currency" block {
                        index = 84
                        length = 3
                        paddingChar = ' '
                        alignment = FieldAlignment.LEFT
                    }
                    "value" block {
                        index = 97
                        length = 10
                        paddingChar = ' '
                        alignment = FieldAlignment.RIGHT
                    }
                }
            }
}