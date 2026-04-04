package br.com.android.buscamed.data.datasource.remote

import br.com.android.buscamed.data.core.network.safeApiCall
import br.com.android.buscamed.data.datasource.remote.dto.PrescriptionResponseDTO
import br.com.android.buscamed.data.datasource.remote.dto.TextRequestDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.File
import javax.inject.Inject

/**
 * Implementação da fonte de dados remota utilizando o Ktor Client.
 *
 * @property httpClient Cliente HTTP configurado para as requisições.
 */
class PrescriptionRemoteDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : PrescriptionRemoteDataSource {

    override suspend fun processPrescriptionText(text: String): PrescriptionResponseDTO {
        return safeApiCall {
            httpClient.post("/v1/prescription/process/text") {
                contentType(ContentType.Application.Json)
                setBody(TextRequestDTO(text = text))
            }
        }
    }

    override suspend fun processPrescriptionImage(file: File): PrescriptionResponseDTO {
        return safeApiCall {
            httpClient.post("/v1/prescription/process/image") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                "image",
                                file.readBytes(),
                                Headers.build {
                                    append(HttpHeaders.ContentType, "image/jpeg")
                                    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                                }
                            )
                        }
                    )
                )
            }
        }
    }
}