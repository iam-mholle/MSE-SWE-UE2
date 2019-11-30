package at.technikum.wien.mse.swe.connector

import at.technikum.wien.mse.swe.SecurityAccountOverviewConnector
import at.technikum.wien.mse.swe.model.SecurityAccountOverview
import java.lang.UnsupportedOperationException
import java.nio.file.Path

class SecurityAccountOverviewConnectorDSLImpl : SecurityAccountOverviewConnector {
    override fun read(file: Path?): SecurityAccountOverview =
            fixedWidthBlock(file ?: throw UnsupportedOperationException("No file path provided.")) {
                "accountNumber" block {
                    index = 40
                    length = 10
                    paddingChar = '0'
                    alignment = FieldAlignment.RIGHT
                }
                "riskCategory" composedOf {
                    "code" block {
                        index = 50
                        length = 2
                    }
                }
                "depotOwner" composedOf {
                    "firstname" block {
                        index = 82
                        length = 30
                        paddingChar = ' '
                        alignment = FieldAlignment.RIGHT
                    }
                    "lastname" block {
                        index = 52
                        length = 30
                        paddingChar = ' '
                        alignment = FieldAlignment.RIGHT
                    }
                }
                "balance" composedOf {
                    "currency" block {
                        index = 112
                        length = 3
                        paddingChar = ' '
                        alignment = FieldAlignment.LEFT
                    }
                    "value" block {
                        index = 115
                        length = 17
                        paddingChar = ' '
                        alignment = FieldAlignment.RIGHT
                    }
                }
            }
}